package main

import (
	"com.gemaestudio/domain"
	"com.gemaestudio/service"
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"html/template"
	"log"
	"net/http"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

//A Page structure
type Page struct {
	Title           string
	Clients         []*domain.Client
	Projects        []*domain.Project
	PortfolioTypes  []*domain.PortfolioType
	TypeId          int
	ProjectId       int
	PortfolioLength int
}

// compile all templates and cache them
var templates *template.Template
var db *sql.DB

// our init function to get our templates path depending on where we are;
// and to setup db access...
func init() {
	dir, _ := os.Getwd() // gives us the source path if we haven't installed.
	templatesPath := filepath.Join(dir, "templates")

	funcMap := template.FuncMap{
		"add": func(args ...interface{}) string {
			ok := false
			var s string
			s, ok = args[0].(string)
			if !ok {
				s = fmt.Sprint(args...)
			}
			v, _ := strconv.Atoi(s)
			return strconv.Itoa(v + 1)
		},
	}
	templates = template.Must(template.New("").Funcs(funcMap).ParseGlob(templatesPath + "/*.html"))

	var err error
	db, err = sql.Open("mysql", "DATABASE_USER:DATABASE_PASSWORD@tcp(127.0.0.1:3306)/DATABASE_NAME")
	if err != nil {
		log.Fatalf("Error on initializing database connection: %s", err.Error())
	}
	db.SetMaxIdleConns(100)
	err = db.Ping() // opens connection to makes sure the database is accessible
	if err != nil {
		log.Fatalf("Error on opening database connection: %s", err.Error())
	}

}

/*********************
 *                   *
 * Main site routes  *
 *                   *
 *********************/

func main() {
	http.HandleFunc("/", index)
	http.HandleFunc("/about/", simpleCallback("about"))
	http.HandleFunc("/clients/", clients)
	http.HandleFunc("/services/", simpleCallback("services"))
	http.HandleFunc("/portfolio/", portfolio)
	http.HandleFunc("/browser/", simpleCallback("browser"))
	http.HandleFunc("/contact/", simpleCallback("contact"))
	http.HandleFunc("/contactSubmit", contactSubmit)
	http.Handle("/assets/", http.StripPrefix("/assets/", http.FileServer(http.Dir("./static"))))
	http.Handle("/assinaturas/", http.StripPrefix("/assinaturas/", http.FileServer(http.Dir("./static/assinaturas"))))

	http.ListenAndServe(":9000", nil)
}

/*********************
 *                   *
 * Handler functions *
 *                   *
 *********************/

//Display the named template
func display(w http.ResponseWriter, tmpl string, data interface{}) {
	// you access the cached templates with the defined name, not the filename
	err := templates.ExecuteTemplate(w, tmpl, data)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
}

func staticHandler(dirPath string) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		fmt.Println("serving", r.URL, r.URL.Path, r.RequestURI)
		http.ServeFile(w, r, r.URL.Path)
	}
}

func simpleCallback(s string) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		defer display(w, s, &Page{Title: "gema - estúdio de idéias"})
	}
}

func index(w http.ResponseWriter, r *http.Request) {
	listClient := new(domain.Client).FindAllMainPage(db)
	// for _, c := range listClient {
	// 	fmt.Println(c.Name)
	// }
	listProject := new(domain.Project).FindAllMainPage(db)
	display(w, "index", &Page{Title: "gema - estúdio de idéias", Clients: listClient, Projects: listProject})
}

func clients(w http.ResponseWriter, r *http.Request) {
	listClient := new(domain.Client).FindAllClientPage(db)
	display(w, "clients", &Page{Title: "gema - estúdio de idéias", Clients: listClient})
}

func portfolio(w http.ResponseWriter, r *http.Request) {
	projectParams := strings.Split(r.URL.Path[11:], "/")
	typeId := 1
	projectId := -1
	if len(projectParams) > 0 && projectParams[0] != "" {
		typeId, _ = strconv.Atoi(projectParams[0])
		if len(projectParams) > 1 {
			projectId, _ = strconv.Atoi(projectParams[1])
			typeId = new(domain.Project).FindById(db, projectId).PortfolioType
		}
	}

	listProjects := new(domain.Project).FindByPortfolioType(db, typeId)
	display(w, "portfolio", &Page{Title: "gema - estúdio de idéias", TypeId: typeId, Projects: listProjects, PortfolioLength: len(listProjects), ProjectId: projectId, PortfolioTypes: new(domain.Project).FindAllPortfolioTypes(db)})
}

func contactSubmit(w http.ResponseWriter, r *http.Request) {
	type ContactResponse struct {
		Status  string
		Message string
	}

	formName := strings.TrimSpace(r.FormValue("name"))
	formEmail := strings.TrimSpace(r.FormValue("email"))
	var response ContactResponse
	if formName == "" || formEmail == "" {
		response = ContactResponse{"KO", "error"}
	} else {
		go service.SendEmail(db, &service.ContactMessage{Name: formName, Email: formEmail, Message: r.FormValue("message")})
		response = ContactResponse{"OK", formName}
	}
	js, err := json.Marshal(response)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}
