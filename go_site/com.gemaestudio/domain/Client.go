package domain

import (
	"database/sql"
	"log"
)

type Client struct {
	Id                       int
	Name                     string
	Image                    string
	EnableMainPageDisplay    bool
	Title                    string
	Description              string
	MainPageBannerImage      string
	MainPageContentLineOne   string
	MainPageContentLineTwo   string
	MainPageContentLineThree string
	MainPageBarImage         string
	PresentationOrder        int
	ProjectId                int
}

func (client *Client) findAll(con *sql.DB, sqlQuery string) (fullListing []*Client) {
	if sqlQuery == "" {
		sqlQuery = "SELECT * FROM clients"
	}

	rows, err := con.Query(sqlQuery)
	if err != nil { /* error handling */
		log.Fatalf("Error on executing query: %s", err.Error())
	}

	fullListing = make([]*Client, 0, 10)
	for rows.Next() {
		exC := new(Client)
		err = rows.Scan(&exC.Id, &exC.Name, &exC.Image, &exC.EnableMainPageDisplay, &exC.Title, &exC.Description, &exC.MainPageBannerImage, &exC.MainPageContentLineOne, &exC.MainPageContentLineTwo, &exC.MainPageContentLineThree, &exC.MainPageBarImage, &exC.PresentationOrder, &exC.ProjectId)
		if err != nil { /* error handling */
			log.Println("Error on scanning data: %s", err.Error())
		}
		fullListing = append(fullListing, exC)
	}

	return
}

func (client *Client) FindAllMainPage(con *sql.DB) (listClients []*Client) {
	return client.findAll(con, "SELECT * FROM clients where enable_main_page_display = 1 order by presentation_order")
}

func (client *Client) FindAllClientPage(con *sql.DB) (listClients []*Client) {
	return client.findAll(con, "SELECT * FROM clients where presentation_order is not NULL order by rand(), presentation_order Limit 12")
}
