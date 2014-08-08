package service

import (
	"bytes"
	"database/sql"
	"log"
	"net/smtp"
	"strconv"
	"text/template"
)

type EmailUser struct {
	Username    string
	Password    string
	EmailServer string
	Port        int
}

type SmtpTemplateData struct {
	FromName  string
	FromEmail string
	To        string
	Subject   string
	Body      string
}

type ContactMessage struct {
	Name    string
	Email   string
	Message string
}

func logContactAttempt(con *sql.DB, msg *ContactMessage) {
	// Prepare statement for reading data
	_, err := con.Exec("INSERT INTO user_contact(name, email, message) values (?, ?, ?)", msg.Name, msg.Email, msg.Message)
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic is best
	}

}

func SendEmail(con *sql.DB, msg *ContactMessage) {

	logContactAttempt(con, msg)

	const emailTemplate = `From: {{.FromName}} [{{.FromEmail}}]
To: {{.To}}
Reply-To: {{.FromEmail}}
Return-Path: {{.FromEmail}}
Subject: {{.Subject}}

{{.Body}}

Mensagem enviada por: {{.FromName}} [{{.FromEmail}}]
`

	var err error
	var doc bytes.Buffer

	context := &SmtpTemplateData{
		msg.Name,
		msg.Email,
		"EMAIL TO",
		"SUBJECT",
		msg.Message,
	}
	t := template.New("emailTemplate")
	t, err = t.Parse(emailTemplate)
	if err != nil {
		log.Print("error trying to parse mail template")
	}
	err = t.Execute(&doc, context)
	if err != nil {
		log.Print("error trying to execute mail template")
	}

	emailUser := &EmailUser{"USER_EMAIL_CONTACT", "USER_EMAIL_PASSWORD", "smtp.gmail.com", 587}

	auth := smtp.PlainAuth("",
		emailUser.Username,
		emailUser.Password,
		emailUser.EmailServer,
	)

	err = smtp.SendMail(emailUser.EmailServer+":"+strconv.Itoa(emailUser.Port), // in our case, "smtp.google.com:587"
		auth,
		emailUser.Username,
		[]string{"atendimento@gemaestudio.com"},
		doc.Bytes())
	if err != nil {
		log.Print("ERROR: attempting to send a mail ", err)
	}
}
