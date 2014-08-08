package com.gemaestudio

import com.typesafe.plugin._
import play.api.Play.current
import play.api.Play
import play.Logger

object EmailNotifier {
  def sendMail (p:User) {
    val mail = use[MailerPlugin].email
    mail.setSubject("SUBJECT")
    mail.addRecipient("EMAIL TO","EMAIL TO")
    mail.addFrom(p.name + "<" + p.email + ">")
    mail.send("Nome: " + p.name + ", Email: "+ p.email + "\n. Mensagem: " + p.message)
  }
}