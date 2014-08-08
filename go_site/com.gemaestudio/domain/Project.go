package domain

import (
	"database/sql"
	"log"
)

type ProjectImage struct {
	Id      int
	Image   string
	Project int
}

type PortfolioType struct {
	Id   int
	Name string
}

type Project struct {
	Id                    int
	PortfolioType         int
	Name                  string
	Image                 string
	EnableMainPageDisplay bool
	Description           string
	MainPageDescription   string
	PresentationOrder     int
	Gallery               []*ProjectImage
}

func (project *Project) findAll(con *sql.DB, sqlQuery string) (fullListing []*Project) {
	if sqlQuery == "" {
		sqlQuery = "SELECT * FROM project"
	}

	rows, err := con.Query(sqlQuery)
	if err != nil { /* error handling */
		log.Fatalf("Error on executing query: %s", err.Error())
	}

	fullListing = make([]*Project, 0, 10)
	for rows.Next() {
		exC := new(Project)
		err = rows.Scan(&exC.Id, &exC.Name, &exC.Image, &exC.PresentationOrder, &exC.PortfolioType, &exC.EnableMainPageDisplay, &exC.Description, &exC.MainPageDescription)
		if err != nil { /* error handling */
			log.Println("Error on scanning Project: %s", err.Error())
		}
		fullListing = append(fullListing, exC)
	}

	return
}

func (project *Project) FindAllMainPage(con *sql.DB) (listProjects []*Project) {
	return project.findAll(con, "SELECT * FROM project where enable_main_page_display = 1 order by presentation_order")
}

func (project *Project) FindByPortfolioType(con *sql.DB, id int) (fullListing []*Project) {
	rows, err := con.Query("SELECT * FROM project where portfolio_type = ?", id)
	if err != nil { /* error handling */
		log.Fatalf("Error on executing query: %s", err.Error())
	}

	fullListing = make([]*Project, 0, 7)
	for rows.Next() {
		exC := new(Project)
		err = rows.Scan(&exC.Id, &exC.Name, &exC.Image, &exC.PresentationOrder, &exC.PortfolioType, &exC.EnableMainPageDisplay, &exC.Description, &exC.MainPageDescription)
		if err != nil { /* error handling */
			log.Println("Error on scanning Project: %s", err.Error())
		}
		exC.Gallery = project.findGallery(con, exC.Id)
		fullListing = append(fullListing, exC)
	}

	return
}

func (project *Project) FindById(con *sql.DB, id int) (exC *Project) {
	exC = new(Project)

	// Prepare statement for reading data
	stmtOut, err := con.Prepare("SELECT * FROM project where id = ?")
	if err != nil {
		log.Fatalf("Error on executing query: %s", err.Error())
	}
	defer stmtOut.Close()

	err = stmtOut.QueryRow(id).Scan(&exC.Id, &exC.Name, &exC.Image, &exC.PresentationOrder, &exC.PortfolioType, &exC.EnableMainPageDisplay, &exC.Description, &exC.MainPageDescription) // WHERE number = 13
	if err != nil {
		log.Println("Error on scanning (by id) a project: %s", err.Error())
	}

	exC.Gallery = project.findGallery(con, exC.Id)

	return
}

func (project *Project) findGallery(con *sql.DB, projectId int) (fullListing []*ProjectImage) {
	rows, err := con.Query("SELECT * FROM project_images where project = ? order by id desc", projectId)
	if err != nil { /* error handling */
		log.Fatalf("Error on executing query: %s", err.Error())
	}

	fullListing = make([]*ProjectImage, 0, 2)
	for rows.Next() {
		exC := new(ProjectImage)
		err = rows.Scan(&exC.Id, &exC.Image, &exC.Project)
		if err != nil { /* error handling */
			log.Println("Error on scanning ProjectImage: %s", err.Error())
		}
		fullListing = append(fullListing, exC)
	}

	return
}

func (project *Project) FindAllPortfolioTypes(con *sql.DB) (fullListing []*PortfolioType) {
	rows, err := con.Query("Select * from portfolio_type where id in (select distinct(portfolio_type) from project)")

	if err != nil { /* error handling */
		log.Fatalf("Error on executing query: %s", err.Error())
	}

	fullListing = make([]*PortfolioType, 0, 3)
	for rows.Next() {
		exC := new(PortfolioType)
		err = rows.Scan(&exC.Id, &exC.Name)
		if err != nil { /* error handling */
			log.Println("Error on scanning PortfolioType: %s", err.Error())
		}
		fullListing = append(fullListing, exC)
	}

	return
}
