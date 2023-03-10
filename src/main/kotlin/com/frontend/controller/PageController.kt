package com.frontend.controller

import com.Env
import com.helper.isValid
import com.helper.withBaseUrl
import com.model.DocumentModel
import com.model.PersonInfoModel
import com.model.UserModel
import com.plugins.ProductSession
import com.plugins.userSession
import com.tables.pojos.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import kotlinx.html.*
import java.util.*

class PageController(call: ApplicationCall) : TemplateController(call) {

    suspend fun landingPage() {
        val productId = call.parameters["i"]?.takeIf { it.isNotBlank() }
        val price = call.parameters["p"]?.takeIf { it.isNotBlank() }

        if (!productId.isNullOrBlank() && !price.isNullOrBlank()) {
            call.sessions.set(ProductSession(productId, price))
        }

        call.respondHtml(HttpStatusCode.OK) {
            // Head Part of Login Page
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Approva System")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

                css("assets/css/landing-page.css")
                css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")


                script {
                    unsafe {
                        +"""const mGBaseUrl = '${Env.BASE_URL}';"""
                    }
                }
            }

            unsafe {
                +"""<style>
           
                |.iti{position: relative; display: block;}
                |.iti__arrow{display: none;}
    
                </style>""".trimMargin()
            }

            body {
                // Splash load for five secons
//                Splash Screen Remove
//                div(classes = "preloader")
                // main landing page after splash
                div("container-fluid") {
                    div(classes = "row landingParent") {
                        div(classes = "col-md-6 landing-left") {
                            h1(classes = "PagarTitle") {
                                +"Pagar con"
                            }
                            img(classes = "karumLogo") {
                                src = "/assets/media/instant-logo.png"
                                height = "50px"
                                width = "200px"
                            }
                            /*p(classes = "landing-left-p") {
                                +"La comprobaci??n de su elegibilidad no afectar?? a su puntuaci??n de cr??dito"
                            }*/

                            button {
                                id = "applyBtnId"
                                onClick = "onApplyBtnClick(this)"
                                +"Solicita aqu?? tu cr??dito"
                            }

                            h4(classes = "howDosWorkTitle") {
                                +"c??mo funciona"
                            }

                            div("stepperContainer") {
                                div("col-md-2") {
                                    div("number-style") {
                                        style = "margin:8px 0px;"
                                        span {
                                            +"1"
                                        }
                                        div {
                                            style = "border-left:1px dashed #182035; height:25px; margin-bottom:4px;"
                                        }
                                        span {
                                            +"2"
                                        }
                                        div {
                                            style = "border-left:1px dashed #182035; height:25px; margin-bottom:4px;"
                                        }
                                        span {
                                            +"3"
                                        }
                                        div {
                                            style = "border-left:1px dashed #182035; height:25px; margin-bottom:4px;"
                                        }
                                        span {
                                            +"4"
                                        }
                                    }
                                }
                                div("col-md-10") {
                                    style = "text-align:left"
                                    p(classes = "landing-stepper-p") {
                                        +"Ingresa toda la informaci??n que se solicita"
                                    }
                                    p(classes = "landing-stepper-p") {
                                        +"Incorpora identificaci??n oficial vigente con firma y foto por ambos lados (INE o pasaporte)"
                                    }
                                    p(classes = "landing-stepper-p") {
                                        +"Incorpora tu ??ltimo recibo de n??mina"
                                    }
                                    p(classes = "landing-stepper-p") {
                                        +"Procesaremos tu solicitud de cr??dito y te notificaremos los siguientes pasos"
                                    }
                                }
                            }
                        }

                        div(classes = "col-md-6 landing-right") {
                            div(classes = "col-md-9") {
                                h5(classes = "landingFirstH5") {
                                    +"Registra ahora tus datos y solicita tu cr??dito"
                                }

                                p(classes = "landingFirstP") {
                                    +"Debes contar con la siguiente documentacion para iniciar tu registro:"
                                }
                            }

                            div(classes = "col-md-9 landingRightContainer") {
                                h5(classes = "identificationH5") {
                                    style = "color:#ff6700;"
                                    +"Identificaci??n Oficial:"
                                }

                                h5 {
                                    style = "color:#182035; font-size:0.875rem; margin-bottom:18px; margin-top:16px"
                                    +"INE/IFE vigente"
                                }

                                h5 {
                                    style = "color:#182035; font-size:0.875rem; margin-bottom:18px; margin-top:16px"
                                    +"Pasaporte vigente"
                                }

                                h5 {
                                    style = "color:#ff6700; margin-bottom:16px;"
                                    +"Recibo de n??mina"
                                }

                              /*  p(classes = "landing-right-p") { +"CFE luz" }
                                p(classes = "landing-right-p") { +"Agua" }
                                p(classes = "landing-right-p") { +"Predial" }
                                p(classes = "landing-right-p") { +"Telmex (l??nea  fija)" }
                                p(classes = "landing-right-p") { +"TV de paga cable fijo" }
                                p(classes = "landing-right-p") { +"Gas natural / Conexi??n fija" }
                                p(classes = "landing-right-p") { +"Contrato de arrendamiento a nombre del solicitante" }
                                p(classes = "landing-right-p") { +"Edos. de cta. bancarios a nombre del solicitante (cheques, ahorro, cr??dito, d??bito)" } */
                            }

                            div(classes = "rightLogoContainer") {
                                img(classes = "landingRightLogo") {
                                    src = "/assets/media/instant-logo.png"
                                    height = "80px"
                                }
                            }
                        }
                    }
                }

                jscript(src = "assets/js/landing-page.js")
            }
        }
    }

    suspend fun welcomePage() {
        val userSession = call.userSession

        val user: User = UserModel.getUser(userSession.mobile) ?: error("Invalid session")

        if (user.status == UserModel.NEW_USER) {
            call.respondRedirect("/identification".withBaseUrl())
            return
        }

        call.respondHtml(HttpStatusCode.OK) {
            // Head Part of Login Page
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Approva System")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

                css("assets/css/info-style.css")
                css("assets/css/welcome-mobile.css")
                css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")


                script {
                    unsafe {
                        +"""const mGBaseUrl = '${Env.BASE_URL}';"""
                    }
                }
            }

            body {
                div("main-container") {
                    header(classes = "info-header") {
                        style = "margin:0;"
                        div("karum-logo") {
                            style = "display: flex; justify-content: center;"
                            img {
                                src = "/assets/media/instant-logo.png"
                                height = "65px"
                            }
                        }
                        /*h1(classes = "splash-title") {
                            style = "font-size:2.25rem; padding-top:30px;"
                            +"Eliminar approva"
                        }*/
                        val headText =
                            if (user.status < UserModel.TC44_API_COMPLETE) {
                                "Bienvenido, continua con tu solicitud, te llevar?? unos minutos."
                            } else {
                                "Complemento al proceso de originaci??n de cr??dito"
                            }
                        h2(classes = "splash-subtitle") {
                            style = "text-align:center; font-size:2rem; padding:30px 0px;"
                            //+"Proceso de originaci??n"
                            +headText
                        }
                    }

                    div(classes = "container welcomeContainer") {
                        form(method = FormMethod.post) {
                            div("container_row") {

                                if (user.status >= UserModel.TC44_API_COMPLETE) {
                                    h4(classes = "welcome-heading") { +"Bienvenido de nuevo" }
                                }

                                /*if (userSession.status == 9) {
                                    span {
                                        style = "display:block; text-align:center; margin-top:20px;"

                                        h5 {
                                            style = "display:inline-block; color:#ffb700; font-size:1.725rem;"
                                            +"N??mero de folio:"
                                        }
                                        h4 {
                                            style = "color:#000; display:inline-block; font-size:1.7rem;"
                                            +"${user?.confirmationCode ?: " "}"
                                        }
                                    }

                                    span(classes = "error") {
                                        id = "folioCodeErrorId"
                                        style = "font-size: 1.4rem; text-align:center; display:none;"
                                        +"N??mero de folio no v??lido o faltante"
                                    }
                                }*/

                                val btnText =
                                    if (user.status >= UserModel.SUMMARY_1_COMPLETE && user.status < UserModel.TC44_API_COMPLETE) {
                                        "Continuar solicitud"
                                    } else {
                                        "Subir documentos"
                                    }

                                div(classes = "iniciarBtnContainer") {
                                    a(classes = "btn btn-primary iniciarBtn") {
//                                        href = "/identification".withBaseUrl()
                                        onClick = "onClickContinueBtn(${user.status})"
                                        +btnText
                                    }
                                }

                            }
                        }
                    }
                }

                jscript(src = "assets/js/main.js")
                jscript(src = "assets/js/welcome-page.js")
            }
        }
    }

    suspend fun documentCheckPage() {
        val userSession = call.userSession
        val user = UserModel.getUser(userSession.mobile) ?: error("Invalid session!")
        val document = DocumentModel.getDocumentByUserId(userSession.userId)
        if (userSession.isReturning.not()) {
            if (pageRedirect()) return
        } else {
            /*if (user.status > UserModel.DOCUMENT_COMPLETE && user.status < UserModel.TC44_API_COMPLETE) {
                if (pageRedirect()) return
            }*/
        }

        call.respondHtml(HttpStatusCode.OK) {
            // Head Part of Login Page
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Approva System")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

                css("assets/css/info-style.css")
                css("assets/css/document-preview-style.css")
                css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

                script {
                    unsafe {
                        +"""const mGBaseUrl = '${Env.BASE_URL}';"""
                    }
                }
            }

            body {
                div("main-container") {
                    header(classes = "info-header") {
                        style = "margin:0;"
                        span("karum-logo") {
                            style = "display: flex; justify-content: center;"
                            img {
                                src = "/assets/media/instant-logo.png"
                                height = "65px"
                            }
                        }
                        h1(classes = "splash-title") {
                            style = "font-size:2.25rem; padding-top:25px;"
                            +"Centro de carga de documentos"
                        }
                    }

                    div(classes = "container documentPageContainer") {
                        form(method = FormMethod.post) {
                            style = "overflow:hidden;"

                            h4 {
                                style = "color:#ff6700; font-size:1.6rem; text-align:center;"
                                +"Da clic en el tipo de documento que deseas cargar"
                            }

                            div {
                                style = "width:100%; margin:80px auto 40px auto; text-align:center;"
                                span("documentUploadSpan") {
                                    h6(classes = "lastUpdateTitle") { +"Ultimo archivo" }
                                    p(classes = "documentUploadDate") {
                                        +"${document?.ineTimestamp ?: "YYYY-MM-DD"}"
                                    }
                                    a {
                                        style = "cursor:pointer;"
                                        href = if (document?.ineFront != null) {
                                            // "/documentPreview?type=".withBaseUrl()            preview already upload documents
                                            "/uploadDocument?type=".withBaseUrl()
                                        } else {
                                            // "/uploadDocument?type=".withBaseUrl()
                                            "/uploadDocument?type=".withBaseUrl()
                                        }
                                        img {
                                            src = "/assets/media/ine-upload.png"
                                            width = "90%"
                                        }
                                    }
                                }

                                span("documentUploadSpan") {
                                    h6(classes = "lastUpdateTitle") { +"Ultimo archivo" }
                                    p(classes = "documentUploadDate") {
                                        +"${document?.passpotTimestamp ?: "YYYY-MM-DD"}"
                                    }
                                    a {
                                        style = "cursor:pointer;"
                                        href = if (document?.passport != null) {
                                            // "/uploadDocument?type=p".withBaseUrl()           preview of already uploaded documents
                                            "/uploadDocument?type=p".withBaseUrl()
                                        } else {
                                            // "/uploadDocument?type=p".withBaseUrl()
                                            "/uploadDocument?type=p".withBaseUrl()
                                        }
                                        img {
                                            src = "/assets/media/pass-upload.png"
                                            width = "90%"
                                        }
                                    }
                                }

                                span("documentUploadSpan") {
                                    h6(classes = "lastUpdateTitle") { +"Ultimo archivo" }
                                    p(classes = "documentUploadDate") {
                                        +"${document?.poaTimestamp ?: "YYYY-MM-DD"}"
                                    }
                                    a {
                                        style = "cursor:pointer;"
                                        href = if (document?.proofOfAddress != null) {
                                            // "/uploadDocument?type=a".withBaseUrl()           preview of already uploaded documents
                                            "/uploadDocument?type=a".withBaseUrl()
                                        } else {
                                            // "/uploadDocument?type=a".withBaseUrl()
                                            "/uploadDocument?type=a".withBaseUrl()
                                        }
                                        img {
                                            src = "/assets/media/poa-upload.png"
                                            width = "90%"
                                        }
                                    }
                                }

                                if (user.status >= UserModel.TC44_API_COMPLETE) {
                                    span("documentUploadSpan") {
                                        h6(classes = "lastUpdateTitle") { +"Ultimo archivo" }
                                        p(classes = "documentUploadDate") {
                                            +"${document?.poiTimestamp ?: "YYYY-MM-DD"}"
                                        }
                                        a {
                                            style = "cursor:pointer;"
                                            href = if (document?.proofOfIncome != null) {
                                                // "/documentPreview?type=i".withBaseUrl()
                                                "/uploadDocument?type=i".withBaseUrl()
                                            } else {
                                                // "/uploadDocument?type=i".withBaseUrl()
                                                "/uploadDocument?type=i".withBaseUrl()
                                            }
                                            img {
                                                src = "/assets/media/poi-upload.png"
                                                width = "90%"
                                            }
                                        }
                                    }
                                }
                            }

                            if (user.status >= UserModel.TC44_API_COMPLETE) {
                                p {
                                    style =
                                        "width:70%; margin:auto; font-size:1rem; font-weight:bold; margin-bottom:25px;"
                                    /*+"""Desp??es de haber cargado tus documentops solicitador por nuestro Call Center, se
                                        continuar?? con el proceso de an??lisis de cr??dito, en caso de requerir informaci??n 
                                        adicional te contactaremos llamando a tu n??mero celular. Muchas gracias. """.trimMargin()*/

                                    +"""Desp??es de haber cargado tus documentos solicitados por nuestra Central de Cr??dito, se 
                                    continuar?? con el proceso de an??lisis de cr??dito, en caso de requerir informaci??n 
                                    adicional te contactaremos llamando a tu n??mero celular. Muchas gracias.""".trimMargin()
                                }
                            }

                            val forwardBtnText = if (user.status >= UserModel.TC44_API_COMPLETE) {
                                "Finalizar carga de documentos"
                            } else {
                                "Continuar solicitud"
                            }

                            a {
                                style =
                                    "float:right; margin-top:-15px; text-align:center; margin-right:10px; cursor:pointer;"
                                onClick = "onClickDocumentForwardBtn(${user.status})"
                                img {
                                    src = "/assets/media/forward.png"
                                    width = "60px"
                                    height = "60px"
                                }
                                p {
                                    style = "font-size:0.75rem; color:#ff6700; margin:0;"
                                    +forwardBtnText
                                }
                            }
                        }
                    }
                }

                jscript(src = "assets/js/main.js")
                jscript(src = "assets/js/document-check-page.js")
            }
        }
    }

    suspend fun documentPreviewPage() {
        val userSession = call.userSession
        val document = DocumentModel.getDocumentByUserId(userSession.userId)
        val type = call.parameters["type"]
        call.respondHtml(HttpStatusCode.OK) {
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Approva System")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

                css("assets/css/info-style.css")
                css("assets/css/document-preview-style.css")
                css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

                script {
                    unsafe {
                        +"""const mGBaseUrl = '${Env.BASE_URL}';"""
                    }
                }
            }

            body {
                div("main-container") {
                    header(classes = "info-header") {
                        span("karum-logo") {
                            style = "display:flex;justify-content: center;"
                            img {
                                src = "/assets/media/instant-logo.png"
                                height = "65px"
                            }
                        }
                        h1(classes = "splash-title-left") {
                            style = "font-size:2.25rem;"
                            +"APPROVA"
                        }
                    }

                    div(classes = "container") {
                        div {
                            h3(classes = "load_center_text") {
                                +"CENTRO DE CARGA"
                            }
                            h4 {
                                style =
                                    "color:#ffb700; text-align:center; margin-top:16px; margin-bottom:24px;text-decoration: underline;"
                                +"Identificaci??n"
                            }
                            h5 {
                                style = "color: #ffb700; text-align: center; margin-bottom:24px;"
                                val subTitle = when (type) {
                                    "p" -> "Pasaporte"
                                    "a" -> "Comprobante de Domicilio (opcional)"
                                    "i" -> "Comprobante de Ingresos"
                                    else -> "IFE/INE"
                                }
                                +subTitle
                            }
                            h5(classes = "timestamp") {
                                val lastUpdate = when (type) {
                                    "p" -> "${document?.passpotTimestamp}"
                                    "a" -> "${document?.poaTimestamp}"
                                    "i" -> "${document?.poiTimestamp}"
                                    else -> "${document?.ineTimestamp}"
                                }
                                +lastUpdate
                            }
                        }

                        div("row") {
                            style = "width:80%; margin:auto; text-align:center"
                            if (type == "") {
                                val ineFront = Base64.getEncoder().encodeToString(document?.ineFront)
                                val ineBack = Base64.getEncoder().encodeToString(document?.ineBack)

                                div("col-md-6") {
                                    style = "padding:10px 15px;"
                                    img {
                                        src = "data:image/png;base64,$ineFront"
                                        width = "85%"
                                    }
                                }

                                div("col-md-6") {
                                    style = "padding:10px 15px;"
                                    img {
                                        src = "data:image/png;base64,$ineBack"
                                        width = "85%"
                                    }
                                }
                            } else {
                                val link = when (type) {
                                    "p" -> Base64.getEncoder().encodeToString(document?.passport)
                                    "a" -> Base64.getEncoder().encodeToString(document?.proofOfAddress)
                                    else -> Base64.getEncoder().encodeToString(document?.proofOfIncome)
                                }
                                div("col-md-3")
                                div("col-md-6 col-sm-12") {
                                    /*style = "display:flex; justify-content:center;"*/
                                    img {
                                        src = "data:image/png;base64,$link"
                                        width = "85%"
                                    }
                                }
                                div("col-md-3")
                            }
                        }

                        div {
                            h5 {
                                style = "color: #ffb700; text-align: center; margin-bottom:16px; margin-top:30px;"
                                +"Haga clic/toque el BOT??N ADELANTE para continuar"
                            }

                            h6 {
                                style = "margin-bottom:16px;"
                                +"(Se sustituira el INE existente)"
                            }

                            a {
                                style = "cursor:pointer;"
                                onClick = "window.history.go(-1); return false;"
//                                href = "/document".withBaseUrl()
                                img {
                                    src = "/assets/media/back.png"
                                    width = "60px"
                                    height = "60px"
                                }
                            }

                            a {
                                style = "cursor:pointer; float:right;"
                                href = "/uploadDocument?type=$type".withBaseUrl()
                                img {
                                    src = "/assets/media/forward.png"
                                    width = "60px"
                                    height = "60px"
                                }
                            }
                        }

                    }
                }

//                jscript(src = "assets/js/document-check-page.js")
            }
        }
    }

    suspend fun calificationPage() {
        val userSession = call.userSession
        val user = UserModel.getUser(userSession.mobile) ?: error("Invalid session")
        if (!Env.DEBUG) {
            if (user.status < UserModel.SUMMARY_1_COMPLETE || user.status > UserModel.TC41_API_COMPLETE) {
                if (pageRedirect()) return
            }
        }
        val tc41Data = PersonInfoModel.getPersonInfoData(userSession.userId)

        if (tc41Data?.isValid() != true) {
            call.respondRedirect("/person_info".withBaseUrl())
            return
        }

        call.respondHtml(HttpStatusCode.OK) {
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Karum Application")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

                css("assets/css/pre-clarification.css")
                css("assets/css/document-mobile.css")
                css("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                script {
                    unsafe {
                        +"""const mGBaseUrl = '${Env.BASE_URL}';""".trimMargin()
                    }
                }

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

                jscript(src = "assets/js/scripts.bundle.min.js")
            }

            body(classes = "clarificationBody") {
                div("main-container") {
                    id = "main-container"
                    header(classes = "header") {
                        span("karum-logo2") {
                            img {
                                src = "/assets/media/instant-logo.png"
                                height = "65px"
                            }
                        }
                        /* h1(classes = "splash-title-left") {
                             style = "display:inline-block; text-shadow: 2px 2px #694620;"
                             +"APPROVA"
                         }*/
                        h2(classes = "splash-subtitle-left") {
                            style = "display:inline-block;"
                            +"Pre - calificaci??n"
                        }
                        h4 {
                            style = "display:inline-block; color:#182035;"
                            +" (paso 1)"
                        }
                        h4(classes = "message") {
                            +"Te queremos conocer mejor, por favor responde las preguntas para poder hacerlo:"
                        }
                    }

                    div(classes = "container") {
                        form(classes = "clarificationFormWrapper") {
                            div(classes = "row") {
                                div(classes = "col-md-10 col-12") {
                                    p(classes = "radioStatement") {
                                        +"""Declaro bajo protesta de decir verdad que no desempe??o actualmente 
                                            ni durante el a??o inmediato anterior alg??n cargo p??blico destacado 
                                            a nivel  federal, estatal, municipal o distrito en M??xico o en el 
                                            extranjero.""".trimMargin()
                                    }
                                }

                                div(classes = "col-md-2 col-12 calif-radio") {
                                    div(classes = "acceptRadioContainer") {
                                        input(
                                            classes = "form-check-input acceptRadioBtn",
                                            type = InputType.radio
                                        ) {
                                            name = "radioBtn01"
                                            checked = true
                                        }
                                        label(classes = "radioLabel") {
                                            +"Si acepto"
                                        }
                                    }

                                    div(classes = "notAcceptRadioContainer") {
                                        input(classes = "form-check-input", type = InputType.radio) {
                                            name = "radioBtn01"
                                        }
                                        label(classes = "radioLabel") {
                                            +"No acepto"
                                        }
                                    }
                                }
                            }
                            div(classes = "row") {
                                style = "margin-top:14px;"
                                div(classes = "col-md-10 col-12") {
                                    p(classes = "radioStatement") {
                                        +"""Declaro tambi??n que mi c??nyuge, en su caso, o pariente por 
                                                    consanguineidad o afinidad hasta el 2?? grado, no desempe??a 
                                                    actualmente ni durante el a??o inmediato anterior ning??n cargo 
                                                    p??blico destacado a nivel federal, estatal, municipal o distrital 
                                                    en M??xico o en el extranjero.""".trimMargin()
                                    }
                                }

                                div(classes = "col-md-2 col-12 calif-radio") {
                                    div(classes = "acceptRadioContainer") {
                                        input(classes = "form-check-input acceptRadioBtn", type = InputType.radio) {
                                            name = "radioBtn02"
                                            checked = true
                                        }
                                        label(classes = "radioLabel") {
                                            +"Si acepto"
                                        }
                                    }
                                    div(classes = "notAcceptRadioContainer") {
                                        input(classes = "form-check-input", type = InputType.radio) {
                                            name = "radioBtn02"
                                        }
                                        label(classes = "radioLabel") {
                                            +"No acepto"
                                        }
                                    }
                                }
                            }
                            div(classes = "row") {
                                style = "margin-top:14px;"
                                div(classes = "col-md-10 col-12") {
                                    p(classes = "radioStatement") {
                                        +"Declaro que ning??n tercero obtendr?? los beneficios derivados de las operaciones realizadas con ???KARUM??? ni ejercer?? los derechos de uso, aprovechamiento o disposici??n de los recursos operados, siendo el verdadero propietario de los mismos."
                                    }
                                }

                                div(classes = "col-md-2 col-12 calif-radio") {
                                    div(classes = "acceptRadioContainer") {
                                        input(classes = "form-check-input acceptRadioBtn", type = InputType.radio) {
                                            name = "radioBtn03"
                                            checked = true
                                        }
                                        label(classes = "radioLabel") {
                                            +"Si acepto"
                                        }
                                    }
                                    div(classes = "notAcceptRadioContainer") {
                                        input(classes = "form-check-input", type = InputType.radio) {
                                            name = "radioBtn03"
                                        }
                                        label(classes = "radioLabel") {
                                            +"No acepto"
                                        }
                                    }
                                }
                            }
                            div(classes = "row") {
                                style = "margin-top:14px;"
                                div(classes = "col-md-10 col-12") {
                                    p(classes = "radioStatement") {
                                        +"Declaro que ning??n tercero aportar?? regularmente recursos para el cumplimiento de las obligaciones derivadas del contrato que se establece con ???KARUM??? sin ser el titular de dicho contrato ni obtener los beneficios econ??micos derivados del mismo."
                                    }
                                }

                                div(classes = "col-md-2 col-12 calif-radio") {
                                    div(classes = "acceptRadioContainer") {
                                        input(classes = "form-check-input acceptRadioBtn", type = InputType.radio) {
                                            name = "radioBtn04"
                                            checked = true
                                        }
                                        label(classes = "radioLabel") {
                                            +"Si acepto"
                                        }
                                    }
                                    div(classes = "notAcceptRadioContainer") {
                                        input(classes = "form-check-input", type = InputType.radio) {
                                            name = "radioBtn04"
                                        }
                                        label(classes = "radioLabel") {
                                            +"No acepto"
                                        }
                                    }
                                }
                            }
                            div(classes = "row") {
                                style = "margin-top:14px;"
                                div(classes = "col-md-10 col-12") {
                                    p(classes = "radioStatement") {
                                        +"Declaro bajo protesta de decir verdad que para efectos de la realizaci??n de las operaciones con ???KARUM??? estoy actuando por cuenta propia."
                                    }
                                }

                                div(classes = "col-md-2 col-12 calif-radio") {
                                    div(classes = "acceptRadioContainer") {
                                        input(classes = "form-check-input acceptRadioBtn", type = InputType.radio) {
                                            name = "radioBtn05"
                                            checked = true
                                        }
                                        label(classes = "radioLabel") {
                                            +"Si acepto"
                                        }
                                    }

                                    div(classes = "notAcceptRadioContainer") {
                                        input(classes = "form-check-input", type = InputType.radio) {
                                            name = "radioBtn05"
                                        }
                                        label(classes = "radioLabel") {
                                            +"No acepto"
                                        }
                                    }
                                }
                            }
                            div(classes = "row") {
                                style = "margin-top:14px;"
                                div(classes = "col-md-10 col-12") {
                                    p(classes = "radioStatement") {
                                        +"Declaro que los recursos que utilizar?? para el pago de este producto provienen de una fuente l??cita. "
                                    }
                                }

                                div(classes = "col-md-2 col-12 calif-radio") {
                                    div(classes = "acceptRadioContainer") {
                                        input(classes = "form-check-input acceptRadioBtn", type = InputType.radio) {
                                            name = "radioBtn06"
                                            checked = true
                                        }
                                        label(classes = "radioLabel") {
                                            +"Si acepto"
                                        }
                                    }

                                    div(classes = "notAcceptRadioContainer") {
                                        input(classes = "form-check-input", type = InputType.radio) {
                                            name = "radioBtn06"
                                        }
                                        label(classes = "radioLabel") {
                                            +"No acepto"
                                        }
                                    }
                                }
                            }
                            div(classes = "row") {
                                div(classes = "col-md-10 col-sm-10") {
                                    p {
                                        id = "notAcceptError"
                                        style =
                                            "text-align:center; color:red; font-size:1.5rem; font-weight:bold; display:none;"
                                        +"Por favor lea y acepte"
                                    }
                                }
                                div(classes = "col-md-2 col-sm-2") {
                                    style = "padding-top:8px; text-align:right; padding-right:0px;"
                                    a(href = "#") {
                                        onClick = "showAuthModal(${user.status})"
                                        img {
                                            src = "/assets/media/forward.png"
                                            width = "60px"
                                            height = "60px"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                div(classes = "modal fade auth-modal") {
                    id = "authModal"
                    role = "dialog"

                    div(classes = "modal-dialog modal-lg") {
                        div("modal-content") {
                            style =
                                "background-color:#fff; border:4px solid #ffdcB3; border-radius:10px; padding:0px 10px;"
                            div("modal-body") {

                                h4 {
                                    style = "color:#ff6700; text-align:left; display:inline-block;font-size: 1.4em;"
//                                    +"Autorizaci??n:"
                                    +"AUTORIZACI??N BUR?? DE CR??DITO:"
                                    span {
                                        style = "color:#000000; font-size:1rem"
                                        +" ${tc41Data.name} ${tc41Data.parentSurname} ${tc41Data.motherSurname} "
                                    }
                                }

                                unsafe {
                                    +"""
                                        <p style="font-size:1rem;">
                                        Por este conducto autorizo expresamente a <b>KARUM OPERADORA DE PAGOS S.A.P.I. DE 
                                        C.V., SOFOM E.N.R.</b>, para que por conducto de sus funcionarios facultados lleve 
                                        a cabo investigaciones, sobre mi comportamiento crediticio en las Sociedades de 
                                        Informaci??n Crediticia que estime conveniente.
                                        </p>""".trimMargin()
                                }
                                br

                                unsafe {
                                    +"""
                                        <p style="font-size:1rem;">
                                        As?? mismo, declaro que conozco la naturaleza y alcance de las Sociedades de Informaci??n 
                                        Crediticias y de la informaci??n contenida en los reportes de cr??dito y reportes de 
                                        cr??dito especiales, declaro que conozco la naturaleza y alcance de la informaci??n 
                                        que se solicitar??, del uso que <b>KARUM OPERADORA DE PAGOS S.A.P.I. DE C.V., SOFOM 
                                        E.N.R.</b>, har?? de tal informaci??n y que ??sta podr?? realizar consultas peri??dicas 
                                        sobre mi historial crediticio o el de la empresa que represento, consintiendo que 
                                        esta autorizaci??n se encuentre vigente por un per??odo de 3 a??os contados a partir 
                                        de su expedici??n y en todo caso durante el tiempo que se mantenga la relaci??n 
                                        jur??dica.
                                        </p>""".trimMargin()
                                }

                                form {
                                    method = FormMethod.post
                                    id = "otp_form"
                                    div {
                                        style =
                                            "width:100%; margin:auto; text-align:center; margin-top:20px; position: relative;"
                                        div {
                                            style =
                                                "display: flex;justify-content: center;flex-direction: column;align-items:center;"
                                            input(
                                                classes = "authInput block-copy-paste numeric numMaxLength",
                                                type = InputType.number
                                            ) {
                                                style = "width: 150px; text-align: center;"
                                                name = "auth_code"
                                                required = true
//                                                placeholder = "Para autorizar ingrese su NIP"
                                                placeholder = "# # # # # #"
                                                maxLength = "6"
                                                minLength = "6"
                                                onKeyDown = "return event.keyCode !== 69"
                                            }
                                        }

                                        a {
                                            style =
                                                "margin-left:90px; color:darkgrey; text-decoration:underline; font-size:0.8rem; position:absolute; top:8px; cursor:pointer;"
                                            onClick = "onClickResendOtp()"
                                            id = "reenviar_codigo_id"
                                            +"Reenviar c??digo"
                                        }

                                        span(classes = "error") {
                                            id = "authCodeErrorId"
                                            style = "font-size:1rem; display:none;"
                                            +"Invalido, error"
                                        }
                                    }
                                }

                                p {
                                    style = "color:#ff6700; text-align: center; margin-top: 10px;"
                                    +"Para autorizar, captura tu c??digo"
                                }

                                div(classes = "row checkBoxRowContainer") {
                                    div {
                                        div(classes = "col-lg-12 form-check form-check-inline") {
                                            style = "display:flex; justify-content:center;"
                                            input(classes = "form-check-input", type = InputType.checkBox) {
                                                style = "min-width: 20px; min-height: 20px;"
                                                id = "acceptDocuments1"
                                                value = ""
                                            }
                                            label(classes = "checkbox_label") {
                                                style =
                                                    "color:blue; text-decoration:underline; display:inline; text-align:left;"
                                                id = "acceptDocumentsLink1"
//                                                +"ACEPTO T??RMINOS Y CONDICIONES DE MEDIOS ELECTRONICOS"
                                                +"ACEPTO T??RMINOS Y CONDICIONES DEL USO DE MEDIOS ELECTR??NICOS"
                                            }
                                        }
                                        span(classes = "error") {
                                            style = "font-size:0.75rem; display:none;"
                                            id = "acceptError1Id"
                                            +"Por favor, reconozca que esta de acuerdo con T??rminos y condiciones de medios electr??nicos"
                                        }
                                    }
                                    div {
                                        div(classes = "col-lg-12 form-check form-check-inline") {
                                            style = "margin-top:25px; display:flex; justify-content:center;"
                                            input(classes = "form-check-input", type = InputType.checkBox) {
                                                style = "min-width: 20px; min-height: 20px;"
                                                id = "acceptDocuments2"
                                                value = ""
                                            }
                                            label(classes = "checkbox_label") {
                                                style =
                                                    "color:blue; text-decoration:underline; display:inline; text-align: left;"
                                                id = "acceptDocumentsLink2"
                                                +"ACEPTO AUTORIZACI??N BUR?? DE CR??DITO"
                                            }
                                        }
                                        span(classes = "error") {
                                            style = "font-size: 0.75rem; display:none;"
                                            id = "acceptError2Id"
                                            +"Por favor, reconozca que esta de acuerdo con Autorizaci??n buro de cr??dito"
                                        }
                                    }
                                    div {
                                        div(classes = "col-lg-12 form-check form-check-inline") {
                                            style = "margin-top:25px; display:flex; justify-content:center;"
                                            input(classes = "form-check-input", type = InputType.checkBox) {
                                                style = "min-width: 20px; min-height: 20px;"
                                                id = "acceptDocuments3"
                                                value = ""
                                            }
                                            label(classes = "checkbox_label") {
                                                style =
                                                    "color:blue; text-decoration:underline; display:inline; text-align: left;"
                                                id = "acceptDocumentsLink3"
//                                                +"ACEPTO T??RMINOS Y CONDICIONES"
//                                                +"ACEPTO AVISO DE PRIVACIDAD E INTERCAMBIO E INTERCAMBIO DE INFORMACI??N"
                                                +"ACEPTACION DEL AVISO DE PRIVACIDAD E INTERCAMBIO DE INFORMACI??N"
                                            }
                                        }
                                        span(classes = "error") {
                                            style = "font-size: 0.75rem; display:none;"
                                            id = "acceptError3Id"
                                            +"Por favor, reconozca que esta de acuerdo con los Terminos y condiciones"
                                        }
                                    }
                                }

                                /*div {
                                    style = "width:80%; margin:auto; margin-top:25px; text-align:center;"
                                    div(classes = "form-check form-check-inline") {
                                        style = "display:flex; justify-content:center;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {

                                        }
                                        label(classes = "checkbox_label") {
                                            style = "color:blue; text-decoration:underline; margin-left: 7px;"

                                            +"ACEPTO T??RMINOS Y CONDICIONES DE MEDIOS ELECTRONICOS"
                                        }
                                    }
                                    span(classes = "error") {

                                    }

                                    div(classes = "form-check form-check-inline") {
                                        style = "display:flex; justify-content:center; margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {

                                        }
                                        label(classes = "checkbox_label") {
                                            style = "color: blue; text-decoration: underline; margin-left: 7px;"

                                            +"ACEPTO AUTORIZACI??N BUR?? DE CR??DITO"
                                        }
                                    }
                                    span(classes = "error") {

                                    }

                                    div(classes = "form-check form-check-inline") {
                                        style = "display:flex; justify-content:center; margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {

                                        }
                                    }
                                    span(classes = "error") {

                                    }
                                }*/

                                a {
                                    style = "float:left; margin-top:5px; margin-left:10px; visibility:hidden"
                                    onClick = "closeAuthModal();"
                                    img {
                                        src = "/assets/media/back.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }

                                a {
                                    style = "float: right; margin-top:5px; margin-right:10px; cursor:pointer"
                                    onClick = "onValidateOTP(this)"
                                    img {
                                        src = "/assets/media/forward.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }
                            }
                        }
                    }
                }

                div(classes = "modal fade success-modal") {
                    id = "revisionModal"
                    role = "dialog"

                    div(classes = "modal-dialog modal-lg") {
                        style = "width:90%; margin:auto; height:auto;"
                        div("modal-content") {
//                            style = "background-color:#858688;"
                            style = "background-color:#fff; border:4px solid #ffdcB3;"
                            div("modal-body") {
                                h4(classes = "dialog-title") {
                                    style = "color:#ff6700; text-align:center;"
                                    +"En revisi??n !"
                                }

                                div {
                                    style = "width:20%; margin:auto; margin-bottom:30px;"
                                    img {
                                        style = "text-align:center; margin-top:90px;"
                                        src = "/assets/media/tick.png"
                                        width = "100px"
                                        height = "100px"
                                    }
                                }

                                h4(classes = "dialog-message") {
//                                    +"Enhorabuena  - Tu solicitud ha sido pre - aprobada"
                                    +"Enhorabuena  - Tu solicitud est?? en proceso"
                                }

                                h5 {
                                    style =
                                        "color:#ff6700; text-align:center; margin-top:10px; font-size:0.875rem; margin-bottom:35px;"
                                    +"Completa la siguiente informaci??n para terminar el tr??mite"
                                }

                                a {
                                    style = "float:left; margin-top:5px; margin-left:10px;"
                                    onClick = "onDismissSuccessModel()"
                                    /*attributes.apply {
                                        put("data-dismiss", "modal")
                                    }*/
                                    img {
                                        src = "/assets/media/back.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }

                                a {
                                    style = "float: right; margin-top:5px; margin-right:10px;"
                                    href = "/supplementaryData".withBaseUrl()
                                    img {
                                        src = "/assets/media/forward.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }
                            }
                        }
                    }
                }

                div(classes = "modal fade success-modal") {
                    id = "authErrorModal"
                    role = "dialog"

                    div(classes = "modal-dialog modal-lg") {
                        style = "width:90%; margin:auto; height:auto;"
                        div("modal-content") {
//                            style = "background-color:#858688;"
                            style = "background-color:#fff; border:4px solid #ffdcB3;"
                            div("modal-body") {
                                h4(classes = "dialog-title") {
                                    style = "color:#ff6700; text-align:center;"
                                    +"Declinado"
                                }

                                div {
                                    style = "width:30%; margin:auto; margin-bottom:50px; text-align:center;"
                                    img {
                                        style = "text-align:center; margin-top:90px;"
                                        src = "/assets/media/sad-emoji.png"
                                        width = "180px"
                                        height = "180px"
                                    }
                                }

                                h4 {
                                    style = "text-align:center; color:#000;"
                                    +"T?? cr??dito ha sido declinado"
                                }

                                a {
                                    style = "float: right; margin-top:5px; margin-right:10px;"
                                    onClick = "onDismissDeclineModel()"
                                    img {
                                        src = "/assets/media/forward.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }
                            }
                        }
                    }
                }

                div(classes = "container") {
                    id = "document1"
                    style = "display:none; height:80vh !important; overflow:auto;"

                    div(classes = "row") {
                        style = "padding:16px 30px; font-size:1.125rem; text-align:left;"
                        div(classes = "col-md-12") {
                            h5 {
                                style = "text-align:center; margin:20px 0px; color:#ff6700; font-weight:bold;"
//                                +"TERMINOS Y CONDICIONES"
                                +"T??RMINOS Y CONDICIONES DEL USO DE MEDIOS ELECTR??NICOS"
                            }

                            unsafe {
                                +"""<p style = "color:#182035;">
                                    <b>KARUM OPERADORA DE PAGOS S.A.P.I. DE C.V., SOFOM E.N.R</b>. (en lo sucesivo ???<b>KARUM</b>???),le ofrece 
                                    a sus Solicitantes, Contratantes y/o Tarjetahabientes (a quienes para efectos de los 
                                    presentes t??rminos y condiciones tambi??n se les denominar?? como ???<b>Cliente</b>???), la posibilidad 
                                    de celebrar la contrataci??n, modificaci??n o cancelaci??n de operaciones y servicios relativos 
                                    a los cr??ditos y programas ofertados por ???<b>KARUM</b>???, as?? como el efectuar Operaciones 
                                    Electr??nicas por los diversos Medios Electr??nicos que pone a su disposici??n, para lo 
                                    cual es IMPORTANTE que previamente a su contrataci??n, lea detenidamente los t??rminos 
                                    y condiciones para su uso que aqu?? se le informan.
                                    </p>""".trimMargin()
                            }

                            p {
                                style = "color:#182035;"
                                +"Definiciones"
                            }

                            ul {
                                style = "color:#182035;"
                                unsafe {
                                    +"""                                        
                                        <li>
                                        Tarjetahabiente. - es el nombre que recibe el usuario de una tarjeta de cr??dito, d??bito, prepago, garantizada y/o lealtad.
                                        </li>
                                        <li>
                                        Solicitante. ??? es la persona f??sica que solicita a ???<b>KARUM</b>??? una tarjeta de cr??dito, d??bito, prepago, garantizada y/o lealtad.
                                        </li>
                                        Contratante. - es la persona que contrata las operaciones o servicios relativos  a los cr??ditos o programas ofrecidos por ???<b>KARUM</b>???.
                                        <li>
                                        Medios Electr??nicos. - los equipos, medios ??pticos o de cualquier otra tecnolog??a, sistemas automatizados de procesamiento de datos y redes de telecomunicaciones, ya sean p??blicos o privados.
                                        </li>
                                        <li>
                                        Operaciones Electr??nicas. - el conjunto de operaciones y servicios que ???<b>KARUM</b>??? realice con sus Clientes a trav??s de Medios Electr??nicos.
                                        </li>
                                    """.trimIndent()
                                }
                            }

                            unsafe {
                                +"""<p style = "color:#182035;">
                                    Al utilizar cualquiera de los servicios que ???<b>KARUM</b>??? pone a su disposici??n a trav??s de 
                                    Medios Electr??nicos considere que acepta t??citamente cumplir con los presentes t??rminos 
                                    y condiciones, mismos que podr??n actualizarse en cualquier momento y se pondr??n a su 
                                    disposici??n en <a href="https://www.karum.com/storage/operadora-terminos-y-condiciones.pdf" target="_blank">https://www.karum.com/storage/operadora-terminos-y-condiciones.pdf</a>
                                    </p>""".trimMargin()
                            }

                            unsafe {
                                +"""<p style = "color:#182035;">
                                    As?? mismo, le recordamos que ???<b>KARUM</b>???se encuentra comprometida con la protecci??n de sus 
                                    Datos Personales por lo que le invitamos a conocer nuestro Aviso de Privacidad 
                                    en <a href="https://www.karum.com/storage/operadora-aviso-de-privacidad-web.pdf" target="_blank">https://www.karum.com/storage/operadora-aviso-de-privacidad-web.pdf</a>
                                    </p>""".trimMargin()
                            }

                            unsafe {
                                +"""<p style = "color:#182035">
                                    En el momento que usted solicita los servicios o productos de <b>KARUM</b> est?? de acuerdo en 
                                    adquirir el car??cter de cliente de la Sociedad. Ponemos a su disposici??n el aviso de 
                                    privacidad integral en la p??gina de internet <a href="https://www.karum.com/storage/operadora-aviso-de-privacidad-web.pdf" target="_blank">https://www.karum.com/storage/operadora-aviso-de-privacidad-web.pdf</a> 
                                    en donde se le da a conocer el detalle del tratamiento que se les dar?? a sus datos 
                                    personales, as?? como los derechos que usted puede hacer valer. En este acto el titular 
                                    de los datos personales otorga su consentimiento expreso a <b>KARUM</b> para tratar sus datos 
                                    personales y para transferirlos a sociedades que formen parte del grupo econ??mico de 
                                    <b>KARUM</b> y/o terceros (con los que <b>KARUM</b> tenga celebrados acuerdos comerciales, 
                                    independientemente de que dicho acuerdo haya concluido, incluidas sociedades de 
                                    informaci??n crediticia, proveedores, subsidiarias, directas o indirectas y partes 
                                    relacionadas) que realicen operaciones de contrataci??n de cr??ditos y prestaci??n de 
                                    servicios, promociones, publicidad, recompensas y dem??s contemplados en nuestro aviso 
                                    de privacidad, as?? como para el mantenimiento o cumplimiento de una relaci??n jur??dica.
                                    </p>""".trimMargin()
                            }

                            /*p {
                                style = "color:#fff;"
                                +"del cr??dito."
                            }*/

                            /*p {
                                style = "color:#fff;"
                                +"""Acepto los t??rminos y condiciones del contrato de adhesi??n registro REC 15227-440-035157/01-03640-0921. 
                                    Estoy conforme y por ende autorizo para que los datos aqui asentados sean investigados 
                                    a efecto de verificar su autenticidad. Asi mismo, estoy de acuerdo que mediante la 
                                    solicitud de los productos o servicios que realice a KUAL/ SERVICIOS INTEGRALES DE 
                                    EMPRENDIMIENTO S.A.PI. DE C.V., SOFOM E.N.R. (hoy KARUM OPERADORA DE PAGOS S.A.P.I.DE C.V., 
                                    SOFOM E.N.R.) en lo sucesivo "KARUM" obtendr?? el caracter de cliente, tambi??n estoy 
                                    de acuerdo que este documento es propiedad exclusiva de KARUM y declaro que he leido 
                                    y entendido el contrato de cr??dito que rige el manejo del cr??dito aqu?? solicitado, que una
                                    versi??n impresa del mismo fue puesta a mi disposici??n y con la firma a continuaci??n 
                                    mi consentimiento para obligarme conforme a su clausulado, mismo que puedo
                                    consultar en la p??gina de internet www.karum.com/credito-para-persond""".trimMargin()
                            }*/
                        }
                    }

                    div {
                        style = "text-align:right;"
                        a {
                            onClick = "onPrivacyForward()"
                            style = "cursor: pointer;"
                            img {
                                src = "/assets/media/forward.png"
                                width = "60px"
                                height = "60px"
                            }
                        }
                    }
                }

                div(classes = "container") {
                    id = "document2"
                    style = "display:none;"

                    div(classes = "row") {
                        style = "padding:16px 30px; font-size:1.125rem; text-align:left;"
                        div(classes = "col-md-12") {
                            h5 {
                                style = "text-align:center; margin:20px 0px; color:#ff6700; font-weight:bold;"
                                +"AUTORIZACI??N BUR?? DE CR??DITO"
                            }

                            unsafe {
                                +"""
                                    <p style="color:#182035;">
                                    Por este conducto autorizo expresamente a <b>KARUM OPERADORA DE PAGOS S.A.P.I. DE C.V., 
                                    SOFOM E.N.R.</b>, para que por conducto de sus funcionarios facultados lleve a cabo investigaciones, 
                                    sobre mi comportamiento crediticio en las Sociedades de Informaci??n Crediticia que estime 
                                    conveniente.
                                    </p>
                                    """.trimMargin()
                            }

                            unsafe {
                                +"""
                                    <p style="color:#182035;">
                                    As?? mismo, declaro que conozco la naturaleza y alcance de las Sociedades de Informaci??n 
                                    Crediticias  y de la informaci??n contenida en los reportes de cr??dito y reportes de 
                                    cr??dito especiales, declaro que conozco la naturaleza y alcance de la informaci??n que 
                                    se solicitar??, del uso que <b>KARUM OPERADORA DE PAGOS S.A.P.I. DE C.V., SOFOM E.N.R.</b>, 
                                    har?? de tal informaci??n y que ??sta podr?? realizar consultas peri??dicas sobre mi historial 
                                    crediticio o el de la empresa que represento, consintiendo que esta autorizaci??n se 
                                    encuentre vigente por un per??odo de 3 a??os contados a partir de su expedici??n y en todo 
                                    caso durante el tiempo que se mantenga la relaci??n jur??dica.
                                    </p>""".trimMargin()
                            }
                        }
                    }

                    div {
                        style = "text-align:right;"
                        a {
                            onClick = "onPrivacyForward()"
                            style = "cursor: pointer;"
                            img {
                                src = "/assets/media/forward.png"
                                width = "60px"
                                height = "60px"
                            }
                        }
                    }
                }

                div(classes = "container") {
                    id = "document3"
                    style = "display:none;"

                    div(classes = "row") {
                        style = "padding:16px 30px; font-size:1.125rem; text-align:left;"
                        div(classes = "col-md-12") {
                            h5 {
                                style = "text-align:center; margin:20px 0px; color:#ff6700; font-weight:bold;"
//                                +"T??RMINOS Y CONDICIONES PARA USO DE MEDIOS ELECTR??NICOS"
                                +"ACEPTACI??N DEL AVISO DE PRIVACIDAD E INTERCAMBIO DE INFORMACI??N"
                            }

                            unsafe {
                                +"""
                                    <p style="color:#182035; font-weight:normal;">
                                    <b>KARUM OPERADORA DE PAGOS S.A.P.I. DE C.V., SOFOM E.N.R</b>. (en lo sucesivo ???<b>KARUM</b>???), con 
                                    domicilio para o??r y recibir notificaciones en Blvd. Manuel ??vila Camacho No. 5, Interior 
                                    S 1000, Ed. Torre B, Piso 10, Of. 1045, Col. Lomas de Sotelo, Naucalpan de Ju??rez, Estado 
                                    de M??xico, C.P. 53390, es el responsable del uso y protecci??n de sus datos personales, en 
                                    ese contexto, le informa que los datos personales recabados, ser??n utilizados para la 
                                    operaci??n de los productos que usted solicite o contrate, as?? como para hacerle llegar 
                                    informaci??n de promociones relacionadas con el mismo. En el momento que usted solicita 
                                    los servicios o productos de <b>KARUM</b> est?? de acuerdo en adquirir el car??cter de cliente de 
                                    la Sociedad. Ponemos a su disposici??n el aviso de privacidad integral en la p??gina de 
                                    internet <a href="https://www.karum.com/storage/operadora-aviso-de-privacidad-web.pdf" target="_blank">https://www.karum.com/storage/operadora-aviso-de-privacidad-web.pdf</a> en donde se 
                                    le da a conocer el detalle del tratamiento que se les dar?? a sus datos personales, as?? 
                                    como los derechos que usted puede hacer valer. En este acto el titular de los datos 
                                    personales otorga su consentimiento expreso a <b>KARUM</b> para tratar sus datos personales 
                                    y para transferirlos a sociedades que formen parte del grupo econ??mico de <b>KARUM</b> y/o 
                                    terceros (con los que <b>KARUM</b> tenga celebrados acuerdos comerciales, independientemente 
                                    de que dicho acuerdo haya concluido, incluidas sociedades de informaci??n crediticia, 
                                    proveedores, subsidiarias, directas o indirectas y partes relacionadas) que realicen 
                                    operaciones de contrataci??n de cr??ditos y prestaci??n de servicios, promociones, publicidad, 
                                    recompensas y dem??s contemplados en nuestro aviso de privacidad, as?? como para el 
                                    mantenimiento o cumplimiento de una relaci??n jur??dica.
                                    </p>""".trimMargin()
                            }

                            /*p {
                                style = "color:#fff;"
                                +"""KUALI SERVICIOS INTEGRALES DE EMPRENDIMIENTO S.A.PI. DE C.V.SOFOM E.N.R., (hoy KARUM OPERADORA DE PAGOS
                                S.A.PI. DE C.V., SOFOM E.N.R.) en adelante "KARUM", le ofrece a sus Solicitantes, Contratantes y/o Acreditados (a quienes
                                para efectos de los presentes t??rminos y condiciones tambi??n se les denominar?? como "Cliente"), la posibilidad de celebrar
                                la contrataci??n, modificaci??n o cancelaci??n de operaciones y servicios relativos a los cr??ditos y programas ofertados por
                                KARUM, asi como el efectuar Operaciones Electr??nicas a trav??s de los diversos Medios Electr??nicos que pone a su
                                disposici??n, para lo cual es IMPORTANTE que previamente a su contrataci??n, lea detenidamente los t??rminos y condiciones
                                para su uso que aqu?? se le informan.""".trimMargin()
                            }
                            p {
                                style = "color:#fff;"
                                +"""Definiciones""".trimMargin()
                            }
                            p {
                                style = "color:#fff;"
                                +"""??? Acreditado es el nombre que recibe la persona a la cual se otorga un cr??dito y/o financiamiento.""".trimMargin()
                            }
                            p {
                                style = "color:#fff;"
                                +"""??? Solicitante persona f??sica que solicita a KARUM la aprobaci??n de un cr??dito, financiamiento y/o programa de lealtad.""".trimMargin()
                            }
                            p {
                                style = "color:#fff;"
                                +"""??? Contratante es la persona que contrata las operaciones o servicios relativos a los cr??ditos o programas ofrecidos por KARUM.""".trimMargin()
                            }*/
                        }
                    }

                    div {
                        style = "text-align:right;"
                        a {
                            onClick = "onPrivacyForward()"
                            style = "cursor: pointer;"
                            img {
                                src = "/assets/media/forward.png"
                                width = "60px"
                                height = "60px"
                            }
                        }
                    }
                }

                modalLoader()
                jscript(src = "assets/js/main.js")
                jscript(src = "assets/js/preclarification.js")
            }
        }
    }

    suspend fun declarationPage() {
        val userSession = call.userSession
        val user = UserModel.getUser(userSession.mobile) ?: error("Invalid session!")
        if (!Env.DEBUG) {
            if (user.status < UserModel.MOBILE_DATA_COMPLETE || user.status >= UserModel.TC44_API_COMPLETE) {
                if (pageRedirect()) return
            }
        }
        call.respondHtml(HttpStatusCode.OK) {
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Approva System")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

                css("assets/css/info-style.css")
                css("assets/css/document-mobile.css")
                css("assets/css/declaration.css")
                css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                script {
                    unsafe {
                        +"""const mGBaseUrl = '${Env.BASE_URL}';""".trimMargin()
                        +"""var mUserStatus = '${user.status}';""".trimMargin()
                    }
                }

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

            }

            body {
                div("main-container") {
                    id = "main-container-declaration"
                    header(classes = "info-header declaration-header") {
                        span("karum-logo2") {
                            img {
                                src = "/assets/media/instant-logo.png"
                                height = "65px"
                            }
                        }
                        /*h1(classes = "splash-title-left") {
                            style = "display:inline-block;"
                            +"APPROVA"
                        }*/
                        h2(classes = "splash-subtitle-left") {
                            style = "display:inline-block; padding-top:15px;"
                            +"Pre - calificaci??n"
                        }
                    }

                    div(classes = "container") {
                        div("row declaration-form-container") {
//                            style = "height:50vh; overflow:auto;"
                            div(classes = "row") {
                                style = "margin:20px 0px; font-size:1rem;"
                                div(classes = "col-md-12") {
                                    p {
                                        style = "font-weight:bolder;"
                                        +"""CONTRATO DE APERTURA DE CR??DITO DE CAR??CTER INDIVIDUAL (EL ???CONTRATO???) QUE CELEBRAN, POR UNA PARTE, KARUM OPERADORA DE PAGOS, S.A.P.I. DE C.V., SOFOM E.N.R.EN LO SUCESIVO LA ???SOFOM???; Y POR OTRA PARTE, LA PERSONA CUYO NOMBRE APARECE EN LA SOLICITUD DE CR??DITO QUE ANTECEDE AL PRESENTE CONTRATO Y QUE SE ADJUNTA COMO ???ANEXO A???, EL CUAL FORMA PARTE INTEGRANTE DEL MISMO, EN LO SUCESIVO, EL ???ACREDITADO???, CONFORME A LOS SIGUIENTES ANTECEDENTES, DECLARACIONES Y CL??USULAS:""".trimMargin()
                                    }

                                    h5(classes = "declarationAntecedents") { +"ANTECEDENTES" }
                                    unsafe {
                                        +"""
                                            <p> 
                                            <b>I.</b> El <b>ACREDITADO</b> ingres?? una solicitud por medios electr??nicos para el otorgamiento de un cr??dito (en lo sucesivo la ???<b>SOLICITUD</b>???), a trav??s de la aplicaci??n m??vil de la <b>SOFOM</b> (en lo sucesivo la ???<b>APLICACI??N</b>???) disponible para dispositivos Android e iOS, o a trav??s del portal de la <b>SOFOM</b> denominado ???<b>APPROVA</b>???, la cual se adjunta como <b>ANEXO A.</b>
                                            <br> <br>
                                            <b>II.</b> El <b>ACREDITADO</b> tuvo a su disposici??n en el portal <b>APPROVA</b> y/o la <b>APLICACI??N</b> de la <b>SOFOM</b> los siguientes documentos: (i) el Aviso de Privacidad vigente de la <b>SOFOM</b> (ii) los t??rminos y condiciones de uso del portal y de la <b>APLICACI??N</b>, as?? como (iii) el Contrato y sus anexos, mismos que fueron aprobados y/o aceptados por el <b>ACREDITADO</b> a trav??s de medios electr??nicos.
                                            </p>
                                            """.trimMargin()
                                    }
                                    h5(classes = "declarationAntecedents") { +"DECLARACIONES" }
                                    unsafe {
                                        +"""
                                            <p>
                                            Declara la ???<b>SOFOM</b>???, a trav??s de su representante legal:
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            a)  Que KUALI SERVICIOS INTEGRALES DE EMPRENDIMIENTO, S.A.P.I DE C.V., SOFOM, E.N.R. (hoy KARUM OPERADORA DE PAGOS, S.A.P.I. DE C.V., SOFOM E.N.R.), se constituy?? al amparo de las leyes mexicanas en los t??rminos del Art??culo 87-B (ochenta y siete guion B) de la Ley General de Organizaciones y Actividades Auxiliares del Cr??dito, como lo acredita con el instrumento notarial n??mero 130,843 (ciento treinta mil ochocientos cuarenta y tres), volumen 1,829 (mil ochocientos veintinueve), del protocolo del Licenciado Ra??l Sicilia Alamilla, titular de la Notar??a n??mero 1 de la Ciudad de Tula, Hidalgo, de fecha 22 de enero de 2019, inscrito en el Registro P??blico de Comercio en Cuautitl??n Estado de M??xico, bajo el folio mercantil n??mero 2019009747 de fecha 11 de febrero de 2019; quedando facultada para operar como una Sociedad Financiera de Objeto M??ltiple, Entidad No Regulada, de conformidad con lo establecido en la Ley General de Sociedades Mercantiles y la Ley General de Organizaciones y Actividades Auxiliares del Cr??dito en vigor. Asimismo, que la <b>SOFOM</b> adopt?? la modalidad de Sociedad An??nima Promotora de Inversi??n de Capital Variable, en t??rminos de la Ley General de Sociedades Mercantiles y la Ley del Mercado de Valores.
                                            <br> <br>
                                            b)	Que KUALI SERVICIOS INTEGRALES DE EMPRENDIMIENTO, S.A.P.I DE C.V., SOFOM, E.N.R. cambi?? de denominaci??n a KARUM OPERADORA DE PAGOS, S.A.P.I DE C.V., SOFOM, E.N.R., tal como se acredita con el instrumento notarial n??mero 123,487 (ciento veintitr??s mil cuatrocientos ochenta y siete), de fecha 6 de agosto de 2021, pasado ante la fe del Lic. Arturo Sobrino Franco titular de la notar??a n??mero 49 de la Ciudad de M??xico.
                                            <br> <br>
                                            c)	 Que de conformidad con el Art??culo 87 J de la Ley General de Organizaciones y Actividades Auxiliares del Cr??dito, la <b>SOFOM</b> para su constituci??n y operaci??n no requiere de autorizaci??n de la Secretar??a de Hacienda y Cr??dito P??blico y est?? sujeta a la supervisi??n de la Comisi??n Nacional Bancaria y de Valores ??nicamente para efectos de lo dispuesto por el art??culo 56 de la ley en comento, tal como se indica en la p??gina de internet <a href="https://karum.com/operadora" target="_blank">https://karum.com/operadora</a>.
                                            <br> <br>
                                            d)	 Que se encuentra debidamente inscrita en el Registro Federal de Contribuyentes bajo el n??mero KSI190122S23.
                                            <br> <br>
                                            e)	 Que su domicilio para o??r y recibir todo tipo de notificaciones est?? ubicado en Blvd. Manuel ??vila Camacho No. 5, Interior S 1000, Edificio Torre B, Piso 10, Oficina 1045, Col. Lomas de Sotelo, C??digo Postal 53390, Naucalpan de Ju??rez, Estado de M??xico.
                                            <br> <br>
                                            f)	 Que se encuentra legitimada para otorgar cr??ditos simples y en cuenta corriente a los <b>ACREDITADOS</b>, para ser dispuestos en cualesquiera tiendas participantes (en lo sucesivo, ???<b>LOS ESTABLECIMIENTOS</b>???) y/o cualquier otro medio autorizado por la <b>SOFOM</b> a los <b>ACREDITADOS</b> que habiendo llenado su SOLICITUD, cumplan con el perfil necesario, a fin de que puedan realizar disposiciones de una l??nea de cr??dito (por l??nea de cr??dito se entiende aquel monto m??ximo de dinero puesto a disposici??n del ACREDITADO con motivo del cr??dito simple y/o en cuenta corriente que le sea otorgado, en lo sucesivo ???L??NEA DE CR??DITO???) dentro del territorio nacional a discreci??n de la <b>SOFOM</b>.
                                            <br> <br>
                                            g)	 Que entre su objeto social, est?? la realizaci??n habitual y profesional de una o m??s de las actividades de otorgamiento de cr??dito, arrendamiento financiero y/o factoraje financiero en los t??rminos de la Ley General de Organizaciones y Actividades Auxiliares de Cr??dito.
                                            <br> <br>
                                            h)   Que el presente Contrato cuenta con un registro vigente ante el Registro de Contratos de Adhesi??n de la <b>CONDUSEF</b> bajo el n??mero: [*].
                                            <br> <br>
                                            i)   Que el <b>ACREDITADO</b> le otorg?? su autorizaci??n para realizar la investigaci??n sobre su historial crediticio en los t??rminos de lo previsto por la Ley para Regular las Sociedades de Informaci??n Crediticia.
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            Declara el <b>ACREDITADO</b>, por su propio derecho:
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            a)	 Ser una persona f??sica mayor de edad con la capacidad legal necesaria para celebrar el presente Contrato y obligarse en los t??rminos que en el mismo se establecen.
                                            <br> <br>
                                            b)	 Que la informaci??n proporcionada a la <b>SOFOM</b>, as?? como aquella contenida en la <b>SOLICITUD</b> que se adjunta al presente Contrato como <b>ANEXO A</b>, es cierta, correcta y completa; as?? como que est?? enterado y conoce de las penas y responsabilidades legales en que incurren las personas que al solicitar un cr??dito o para obtenerlo, falsean, alteran u ocultan informaci??n relevante para el otorgamiento o negativa del mismo.  
                                            <br> <br>
                                            c)	 Que ha otorgado su autorizaci??n a la <b>SOFOM</b> para realizar investigaciones sobre su historial crediticio en t??rminos de lo previsto por la Ley para Regular las Sociedades de Informaci??n Crediticia.
                                            <br> <br>
                                            d)   Que ha le??do el presente Contrato, comprende sus alcances legales, consecuencias de los derechos y obligaciones que asume y est?? de acuerdo con todas y cada una de las estipulaciones contenidas en el presente, por lo que al firmarlo se obliga a sujetarse en los t??rminos y condiciones aqu?? pactadas relativas a estructura y operaci??n del cr??dito que se le otorga, su plazo, tasa y consecuencias por el incumplimiento de sus obligaciones.
                                            <br> <br>
                                            e)   Que los recursos con los cuales ha de pagar el cr??dito dispuesto han sido o ser??n obtenidos o generados a trav??s de una fuente de origen l??cito y que el destino que dar?? a los recursos obtenidos al amparo del presente Contrato ser?? tan s??lo a fines permitidos por la ley y que no se encuentran dentro de los supuestos establecidos en los art??culos 139 Qu??ter y 400 bis del C??digo Penal Federal.
                                            <br> <br>
                                            f)   Que con anterioridad al llenado de la <b>SOLICITUD</b> y previo a la celebraci??n del presente Contrato, la <b>SOFOM</b> puso a su disposici??n su Aviso de Privacidad, en t??rminos de la Ley Federal de Protecci??n de Datos Personales en Posesi??n de los Particulares (en lo sucesivo ???<b>LFPDPPP</b>???), donde se se??ala, adem??s del tratamiento que se dar?? a sus datos personales, los derechos de acceso, rectificaci??n, cancelaci??n, oposici??n, revocaci??n, limitaci??n en el uso y/o divulgaci??n con los que cuenta y la forma c??mo los puede hacer valer.
                                            <br> <br>
                                            g)   Que es su voluntad celebrar el presente Contrato a trav??s de medios electr??nicos, manifestando su consentimiento a trav??s de los procesos de autenticaci??n establecidos en la <b>APLICACI??N</b> y/o <b>APPROVA</b> de la <b>SOFOM</b>, de conformidad con los art??culos 80, 89 bis, 90 y 93 del C??digo de Comercio.
                                            </p>
                                            """.trimMargin()
                                    }
                                    h5(classes = "declarationAntecedents") { +"CL??USULAS:" }
                                    unsafe {
                                        +"""
                                            <p>
                                            <b>PRIMERA. OBJETO Y L??MITE DE CR??DITO</b>. Una vez que (i) la <b>SOLICITUD</b> sea aprobada y (ii) el presente Contrato se haya celebrado; la <b>SOFOM</b> otorgar?? al <b>ACREDITADO</b> una <b>L??NEA DE CR??DITO</b> disponible en cr??dito simple o en cuenta corriente en moneda nacional, con l??mite hasta por la cantidad indicada en la car??tula de cr??dito del Contrato, misma que se agrega a este instrumento como <b>ANEXO B</b>: al respecto, el <b>ACREDITADO</b> manifiesta que tiene capacidad econ??mica suficiente para cumplir con los pagos de manera oportuna por el l??mite concedido (en adelante ???<b>L??MITE DE CR??DITO</b>???), incluyendo las variaciones de dicha L??NEA DE CR??DITO seg??n lo previsto en las cl??usulas de este Contrato.
                                            <br> <br>
                                            Dentro del <b>L??MITE DE CR??DITO</b> no quedan comprendidos los intereses, las comisiones y los gastos de cobranza que se indican en el <b>ANEXO B</b>.
                                            <br> <br>
                                            Para otorgar el cr??dito, la <b>SOFOM</b> ha tomado en cuenta los an??lisis de informaci??n cuantitativa y cualitativa del <b>ACREDITADO</b>.
                                            <br> <br>
                                            La <b>SOFOM</b> s??lo podr?? otorgar cr??dito a personas mayores de edad, quedando prohibido el otorgamiento del mismo a personas menores de edad o incapaces legalmente.
                                            <br> <br>
                                            Adem??s de la celebraci??n de este Contrato es necesario que el <b>ACREDITADO</b> acepte a trav??s de la <b>APLICACI??N</b>, los t??rminos y condiciones bajo los cuales la <b>SOFOM</b> autorizar?? su <b>SOLICITUD</b> por medios electr??nicos.
                                            <br> <br>
                                            El Contrato y sus anexos ser??n enviados al <b>ACREDITADO</b> por correo electr??nico y se mantendr??n a su disposici??n en el portal de internet <a href="https://karum.com/operadora-activacel" target="_blank">https://karum.com/operadora-activacel</a> y la <b>APLICACI??N</b>.
                                            <br> <br>
                                            El <b>ACREDITADO</b> reconoce y acepta que la <b>SOFOM</b> podr?? formular oferta(s) al <b>ACREDITADO</b> para incrementar el <b>L??MITE DE CR??DITO</b> de acuerdo con las pol??ticas internas de la <b>SOFOM</b>. Asimismo, en t??rminos del art??culo 294 de la Ley General de T??tulos y Operaciones de Cr??dito, el <b>ACREDITADO</b> autoriza a la <b>SOFOM</b> a disminuir el <b>L??MITE DE CR??DITO</b> de conformidad con las pol??ticas internas de la <b>SOFOM</b> notific??ndole por escrito o por medios electr??nicos al ACREDITADO de este hecho.
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            Para que la <b>SOFOM</b> incremente o disminuya el <b>L??MITE DE CR??DITO</b> considerar??: (i) el historial crediticio del <b>ACREDITADO</b> en los ??ltimos 6 (seis) meses, (ii) los cargos realizados, (iii) los pagos efectuados, y (iv) el comportamiento en el cumplimiento de sus obligaciones crediticias derivadas de este instrumento y con terceros.
                                            <br> <br>
                                            Trat??ndose de incrementos al <b>L??MITE DE CR??DITO</b>, la <b>SOFOM</b> le notificar?? al <b>ACREDITADO</b> una oferta para incrementarlo, de manera escrita, mediante el estado de cuenta, correo electr??nico, telef??nicamente y/o bien, por cualquier medio electr??nico. Una vez notificado, el <b>ACREDITADO</b> tendr?? un plazo m??ximo de 60 (sesenta) d??as para aceptar expresamente el aumento de su <b>L??MITE DE CR??DITO</b> por cualquier medio indicado en la notificaci??n de la oferta. Transcurrido dicho plazo caducar?? la oferta de incrementar el <b>L??MITE DE CR??DITO</b> y la <b>SOFOM</b> mantendr?? el <b>L??MITE DE CR??DITO</b> sin cambio alguno.
                                            <br> <br>
                                            La <b>SOFOM</b> podr?? declarar inactiva la cuenta del <b>ACREDITADO</b> y podr?? revocar el cr??dito otorgado, si transcurren 6 (seis) meses sin compra y/o cargo alguno realizado. La <b>SOFOM</b> podr?? reactivar la cuenta del <b>ACREDITADO</b> a solicitud del mismo siempre y cuando ??ste siga cumpliendo con los requisitos iniciales de contrataci??n, como lo es tener un puntaje suficiente de score creditico a juicio de la <b>SOFOM</b>, en la Sociedad de Informaci??n Crediticia.
                                            <br> <br>
                                            <b>SEGUNDA. MEDIOS DE DISPOSICI??N.</b> El <b>ACREDITADO</b> contar?? con diversas opciones que la <b>SOFOM</b> le habilitar?? para realizar la(s) disposici??n(es) de su cr??dito simple y/o en cuenta corriente y/o de su <b>L??NEA DE CR??DITO</b>, entre los que se incluyen, pero no se limitan: medios electr??nicos, aplicaciones m??viles, tarjeta pl??stica, tarjeta digital o vinculada y/o disposiciones en efectivo dentro de territorio nacional, a discreci??n de la <b>SOFOM</b> (en adelante ???<b>MEDIOS DE DISPOSICI??N</b>???).
                                            <br> <br>
                                            Trat??ndose de ??rdenes de compra y/o servicios que el <b>ACREDITADO</b> y/o la(s) persona(s) que este haya autorizado como adicional(es) para disponer de su <b>L??NEA DE CR??DITO</b>, que sean efectuados dentro de territorio nacional y/o en el extranjero por v??a telef??nica, internet u otros medios electr??nicos, conforme a los t??rminos de la autorizaci??n proporcionada por la <b>SOFOM</b>, el <b>ACREDITADO</b> quedar?? como responsable ??nico por las compras y servicios adquiridos, as?? como de verificar que la persona o medio al cual le proporcione la informaci??n sobre sus <b>MEDIOS DE DISPOSICI??N</b> est?? verdaderamente autorizada o vinculada con el establecimiento para tal efecto. Trat??ndose de operaciones electr??nicas que se realicen por tel??fono o a trav??s de internet, se llevar??n a cabo los procesos de verificaci??n de identidad del <b>ACREDITADO</b> que se estimen necesarios.
                                            <br> <br>
                                            La <b>SOFOM</b> no asume responsabilidad en caso de que otras instituciones o establecimientos se reh??sen a admitir el uso de los <b>MEDIOS DE DISPOSICI??N</b> ni en caso de que no puedan efectuarse disposiciones debido a cualquier tipo de desperfecto y/o suspensi??n del servicio en equipos automatizados, cajeros autom??ticos, sistemas telef??nicos y/o electr??nicos, entre otros.
                                            <br> <br>
                                            El <b>ACREDITADO</b>, cuando la <b>SOFOM</b> as?? lo habilite, podr?? disponer en efectivo de la <b>L??NEA DE CR??DITO</b> mediante la obtenci??n de dinero en efectivo a trav??s de la red de comisionistas, y/o cajeros autom??ticos, lo anterior mediante la suscripci??n de comprobantes de disposici??n o de otros documentos o medios que sean aceptados por la <b>SOFOM</b> y/o a trav??s de cualquier <b>MEDIO DE DISPOSICI??N</b> inclusive electr??nicos que est??n habilitados por la <b>SOFOM</b>, dentro de los l??mites, condiciones y comisiones que se tengan establecidos. La <b>SOFOM</b> podr?? restringir o limitar las disposiciones de efectivo de la <b>L??NEA DE CR??DITO</b> lo cual informar?? al <b>ACREDITADO</b> en su estado de cuenta. En todo momento, las disposiciones en efectivo de la <b>L??NEA DE CR??DITO</b> ser??n consideradas como disposiciones del cr??dito simple y/o en cuenta corriente y de la propia <b>L??NEA DE CR??DITO</b>.
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            Por razones de identificaci??n o de seguridad y a solicitud de la <b>SOFOM</b>, de <b>LOS ESTABLECIMIENTOS</b> y/o comisionistas, el <b>ACREDITADO</b> acepta presentar, cuando le sea requerida, una identificaci??n oficial vigente con fotograf??a y firma, al realizar disposiciones de la <b>L??NEA DE CR??DITO</b>.
                                            <br> <br>
                                            El <b>ACREDITADO</b> ser?? responsable del mal uso que se haga del N??mero de Identificaci??n Personal (en lo sucesivo "<b>NIP</b>") o de cualquier otra firma electr??nica y/o de cualquier clave de acceso o de identificaci??n personal que se implemente en el futuro, que permitan la utilizaci??n de los <b>MEDIOS DE DISPOSICI??N</b> y dem??s medios electr??nicos, sistemas electr??nicos, automatizados o telecomunicaci??n.
                                            <br> <br>
                                            En caso que el <b>ACREDITADO</b> convenga con la <b>SOFOM</b> utilizar una tarjeta pl??stica (en adelante la ???<b>TARJETA PL??STICA</b>???) o digital (en adelante la ???<b>TARJETA DIGITAL</b>???) como <b>MEDIOS DE DISPOSICI??N</b> de su <b>L??NEA DE CR??DITO</b> (en adelante conjunta e indistintamente la ???<b>TARJETA</b>??? o las ???<b>TARJETAS</b>??? en plural), la <b>SOFOM</b> deber?? de poner a disposici??n del <b>ACREDITADO</b> dichas <b>TARJETAS</b> para su uso exclusivo, las cuales ser??n personales e intransferibles, en el entendido que ser??n de exclusiva propiedad de la <b>SOFOM</b> y que conservar?? el <b>ACREDITADO</b> en calidad de depositario.
                                            <br> <br>
                                            El <b>ACREDITADO</b> podr?? solicitar por escrito o a trav??s de medios electr??nicos a la <b>SOFOM</b> la expedici??n de TARJETAS adicionales compartiendo el <b>L??MITE DE CR??DITO</b> otorgado en el presente Contrato, en su totalidad o solo un porcentaje de ??ste seg??n sea requerido por el <b>ACREDITADO</b>, quedando a entera discreci??n de la <b>SOFOM</b> el aceptar o rechazar este tipo de solicitudes sobre <b>TARJETAS</b> adicionales.
                                            <br> <br>
                                            Una vez que el <b>ACREDITADO</b> reciba su <b>TARJETA F??SICA</b> y previo a su utilizaci??n, el <b>ACREDITADO</b> deber?? firmarla y a partir de ese momento quedar?? como ??nico responsable del uso de la misma. Los mismos t??rminos aplicar??n para el caso de <b>TARJETAS F??SICAS</b> adicionales. Por lo anterior, la <b>SOFOM</b> queda liberada de toda responsabilidad por el uso indebido de dichas <b>TARJETAS F??SICAS</b>. El <b>ACREDITADO</b> y/o las personas autorizadas para utilizar las <b>TARJETAS</b> adicionales, por el simple uso y aceptaci??n de las <b>TARJETAS</b>, se obligan t??citamente a cumplir con todas y cada una de las estipulaciones del presente Contrato, incluyendo sus anexos. Los usuarios de las <b>TARJETAS</b> adicionales que, en su caso sean expedidas, no ser??n considerados como obligados solidarios del <b>ACREDITADO</b>.
                                            <br> <br>
                                            Como medida de seguridad la <b>SOFOM</b> utiliza al menos un elemento para autenticar las operaciones autorizadas por el <b>ACREDITADO</b> y/o usuarios de las <b>TARJETAS</b> adicionales, ya sea al momento de realizar la operaci??n, o bien, al momento de entregar el bien o servicio adquirido en virtud de dicha operaci??n. El referido elemento puede ser informaci??n que la <b>SOFOM</b> proporcione al <b>ACREDITADO</b> o usuarios de las <b>TARJETAS</b> adicionales o permita a estos generar, para ingresarlas al sistema, tales como contrase??a, <b>NIP</b>, cualquier otra firma electr??nica o informaci??n contenida o generada por medios o dispositivos electr??nicos, as?? como informaci??n derivada de caracter??sticas propias del <b>ACREDITADO</b> o usuarios de las <b>TARJETAS</b> adicionales en su caso,  tales como huellas dactilares, geometr??a de la mano o de la cara, patrones de retina, entre otros, o aquella otra informaci??n autorizada por Banco de M??xico.
                                            <br> <br>
                                            El <b>ACREDITADO</b> acepta que como medio de manifestaci??n de su voluntad, en el uso de los <b>MEDIOS DE DISPOSICI??N</b> y/o en las disposiciones de la <b>L??NEA DE CR??DITO</b>, se utilizar??, seg??n sea el caso, su firma aut??grafa, la presentaci??n y/o uso de la <b>TARJETAS</b> en establecimientos y/o comisionistas y/o su Firma Electr??nica y/o <b>NIP</b>. El suministro de la informaci??n de las <b>TARJETAS</b> v??a voz (tel??fono), o por medios electr??nicos, o la presentaci??n y uso de las <b>TARJETAS</b> en otros equipos electr??nicos o automatizados de <b>LOS ESTABLECIMIENTOS</b>, constituye tambi??n medios de manifestaci??n de la voluntad.
                                            <br> <br>
                                            <b>TERCERA. ENTREGA Y ACTIVACI??N DE LA TARJETA PL??STICA</b>. Una vez que (i) la <b>SOLICITUD</b> sea aprobada y (ii) el presente Contrato se haya celebrado, la <b>SOFOM</b> dentro de un plazo que no exceder?? de 15 (quince) d??as h??biles posteriores a la celebraci??n del presente acuerdo, le entregar?? al <b>ACREDITADO</b> personalmente (o a la persona que se identifique y se encuentre en el domicilio se??alado en la <b>SOLICITUD</b>) la(s) <b>TARJETA(s) F??SICA(S)</b> que la <b>SOFOM</b> hubiere emitido para uso exclusivo del <b>ACREDITADO</b> y/o sus adicionales.
                                            <br> <br>
                                            El plazo establecido en el p??rrafo que antecede para la entrega de la(s) <b>TARJETA(S) F??SICA(S)</b> podr?? ser modificado si por causas imputables al <b>ACREDITADO</b> no se puede realizar la entrega; incluyendo, de forma enunciativa m??s no limitativa, que el <b>ACREDITADO</b> haya proporcionado de forma err??nea o incompleta los datos de su domicilio y/o domicilio alterno.
                                            <br> <br>
                                            Las <b>TARJETAS F??SICAS</b> se entregar??n desactivadas y para su activaci??n el <b>ACREDITADO</b> deber??: (i) solicitar activarlas v??a telef??nicay seguir el proceso correspondiente en el Centro de Atenci??n Telef??nica de la <b>SOFOM</b>.
                                            <br> <br>
                                            No ser??n procedentes los cargos a las <b>TARJETAS</b> que no est??n activadas, tampoco ser??n aplicables otros cargos previamente autorizados por el <b>ACREDITADO</b>, cuando se sustituyan las <b>TARJETAS F??SICAS</b> y ??stas no hayan sido activadas.
                                            <br> <br>
                                            <b>CUARTA. ASIGNACI??N DE NIP Y RECOMENDACIONES</b>. Uno de los factores de autenticaci??n del <b>ACREDITADO</b> que podr?? ser solicitado para efectuar disposiciones de su <b>L??NEA DE CR??DITO</b>, es el <b>NIP</b>, este ser?? generado por la <b>SOFOM</b> a trav??s de los mecanismos adecuados durante el proceso de activaci??n de la <b>TARJETA</b> que el <b>ACREDITADO</b> lleve a cabo en el Centro de Atenci??n Telef??nica (cuyos datos se indican al reverso de la <b>TARJETA F??SICA</b> y/o en los <b>MEDIOS DE DISPOSICI??N</b>).
                                            <br> <br>
                                            Asimismo, el <b>ACREDITADO</b> podr?? registrar elementos biom??tricos de autenticaci??n (en lo sucesivo los "<b>BIOM??TRICOS</b>") a trav??s de la <b>APLICACI??N</b> de la <b>SOFOM</b> para identificarse.
                                            <br> <br>
                                            En relaci??n con el <b>NIP</b> la <b>SOFOM</b> realiza las siguientes recomendaciones al <b>ACREDITADO</b>:
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            a)	No compartir su <b>NIP</b> con terceros.
                                            <br> <br>
                                            b)	No grabar el <b>NIP</b> en la <b>TARJETA F??SICA</b> o guardarlo junto con ella.
                                            <br> <br>
                                            c)	Destruir el documento con el <b>NIP</b> una vez memorizado.
                                            <br> <br>
                                            d)	Evita dar clic o entrar a sitios desconocidos o sospechosos.
                                            <br> <br>
                                            e)	La <b>SOFOM</b> nunca te solicitar?? datos por ning??n medio (<b>NIP</b>, contrase??as, c??digo de seguridad o token).
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            El <b>ACREDITADO</b> reconoce y acepta que el <b>NIP</b>, el <b>BIOM??TRICO</b>, o cualquier otro n??mero confidencial y/o contrase??a que  llegare a convenir con la <b>SOFOM</b>, equivalen a su firma electr??nica y son medios que lo identifican  al realizar las disposiciones de la <b>L??NEA DE CR??DITO</b>, as?? como para la adquisici??n de bienes o servicios; mediante el uso de equipos automatizados, y al realizar las dem??s operaciones con dichos equipos que se previenen en este Contrato,  as?? como por los medios electr??nicos previamente contratados.
                                            <br> <br> 
                                            El <b>ACREDITADO</b> expresamente reconoce y acepta que la <b>TARJETA</b> es de uso personal e intransferible y el <b>NIP</b>  o cualquier otro n??mero confidencial y/o contrase??a que llegare a convenir con la <b>SOFOM</b> son confidenciales.
                                            <br> <br>
                                            El <b>ACREDITADO</b> reconoce y acepta que ser??n de su exclusiva responsabilidad las disposiciones que se realicen derivado de cualquier uso indebido que terceros no autorizados llegaren a hacer de la <b>TARJETA</b>, del <b>NIP</b> o cualquier otro n??mero confidencial y/o contrase??a que llegare a convenir con la <b>SOFOM</b>.
                                            <br> <br>
                                            <b>QUINTA. DISPOSICIONES EN CAJEROS AUTOM??TICOS Y MEDIOS ELECTR??NICOS</b>. A efecto de realizar las operaciones a que se refiere este Contrato, la <b>SOFOM</b> y el <b>ACREDITADO</b> convienen en que las <b>TARJETAS F??SICAS</b> podr??n estar habilitadas o no para acceder a cajeros autom??ticos o para llevar a cabo operaciones v??a internet, seg??n lo determine la <b>SOFOM</b> al momento de la aprobaci??n de la <b>SOLICITUD</b> del <b>ACREDITADO</b>.
                                            <br> <br>
                                            Sin perjuicio de lo anterior, el <b>ACREDITADO</b> podr?? solicitar a trav??s de la <b>APLICACI??N</b> que la <b>TARJETA F??SICA</b> sea habilitada o deshabilitada para realizar disposiciones de su <b>L??NEA DE CR??DITO</b>, previa aprobaci??n de la <b>SOFOM</b>.
                                            <br> <br>
                                            <b>SEXTA. FORMA DE ACCEDER O DISPONER DEL CR??DITO Y COBERTURA</b>. El <b>ACREDITADO</b> podr?? disponer del cr??dito concedido por la SOFOM dentro de territorio nacional y/o en el extranjero, a discreci??n de la <b>SOFOM</b>, a trav??s de los <b>MEDIOS DE DISPOSICI??N</b> pactados, consintiendo cada operaci??n, ya sea mediante la expedici??n de un comprobante f??sico o electr??nico de la transacci??n correspondiente en favor de la <b>SOFOM</b>. El <b>ACREDITADO</b> podr?? disponer de su <b>L??MITE DE CR??DITO</b>, a discreci??n de la <b>SOFOM</b> conforme a lo siguiente: i) total o parcialmente, ii) en una sola operaci??n o en varias disposiciones parciales, iii) a trav??s de cr??ditos simples o cuenta corriente, y iv) en programas a meses con o sin intereses, sin que la suma del importe de las operaciones sobrepase el <b>L??MITE DE CR??DITO</b> concedido por la <b>SOFOM</b>.
                                            <br> <br>
                                            Por razones de identificaci??n y de seguridad, el <b>ACREDITADO</b> al disponer de su <b>L??NEA DE CR??DITO</b> deber?? presentar en <b>LOS ESTABLECIMIENTOS</b> o con los comisionistas, cuando sea aplicable, adem??s de la <b>TARJETA F??SICA</b>, una identificaci??n oficial vigente con fotograf??a y firma que a simple vista sea similar a la estampada al reverso de la <b>TARJETA F??SICA</b>, el <b>NIP</b> o alg??n <b>BIOM??TRICO</b> para hacer disposiciones en los t??rminos previstos en el presente Contrato, de lo contrario los establecimientos o comisionistas podr??n negar la autorizaci??n del servicio.
                                            <br> <br>
                                            El <b>ACREDITADO</b> documentar?? las disposiciones de su <b>L??NEA DE CR??DITO</b> mediante la suscripci??n de pagar??s o cualquier otro documento o medio autorizado por la <b>SOFOM</b> y aceptado por <b>LOS ESTABLECIMIENTOS</b> o comisionistas. Los pagar??s y cualesquiera otros documentos se suscribir??n y expedir??n a la orden de la <b>SOFOM</b> y ser??n entregados por el <b>ACREDITADO</b> a <b>LOS ESTABLECIMIENTOS</b> y/o comisionistas.
                                            <br> <br>
                                            Las sumas que el <b>ACREDITADO</b> disponga con cargo a su <b>L??NEA DE CR??DITO</b> dentro del territorio nacional, ser??n documentadas invariablemente en moneda en curso legal en los Estados Unidos Mexicanos.
                                            <br> <br>
                                            <b>LOS ESTABLECIMIENTOS</b> se reservan el derecho de admitir o no el pago de mercanc??a, consumos, o servicios con cargo a la <b>L??NEA DE CR??DITO</b> a que se refiere el presente Contrato cuando el <b>ACREDITADO</b> no presente identificaci??n oficial con fotograf??a y firma y/o cuando la firma estampada en el pagar?? o documento que instrumente la disposici??n del cr??dito sea a simple vista distinta a la estampada al reverso de la <b>TARJETA F??SICA</b>. La <b>SOFOM</b> no asume responsabilidad en caso de que <b>LOS ESTABLECIMIENTOS</b> se reh??sen a admitir el uso de la <b>TARJETA F??SICA</b> y/o de cualquier otro <b>MEDIO DE DISPOSICI??N</b> y/o en caso de que no puedan efectuarse disposiciones por desperfecto o suspensi??n del servicio en equipos automatizados, sistemas telef??nicos y/o electr??nicos, entre otros.
                                            <br> <br>
                                            La <b>SOFOM</b> es ajena a las relaciones mercantiles o civiles existentes o que surjan entre el <b>ACREDITADO</b> y <b>LOS ESTABLECIMIENTOS</b> o entre el <b>ACREDITADO</b> y aqu??llos a quienes se efect??en pagos por orden de ??ste y con cargo a la <b>L??NEA DE CR??DITO</b> otorgada.
                                            <br> <br>
                                            La <b>SOFOM</b> no asumir?? responsabilidad alguna por la calidad, cantidad, precio, garant??as, plazo de entrega o cualesquiera otras caracter??sticas de los bienes o servicios que se adquieran en <b>LOS ESTABLECIMIENTOS</b> mediante el uso de los <b>MEDIOS DE DISPOSICI??N</b>. Consecuentemente, cualquier derecho que llegare a asistir al <b>ACREDITADO</b> por los conceptos citados, deber?? hacerse valer directamente en contra de los referidos establecimientos.
                                            <br> <br>
                                            La <b>SOFOM</b>, no podr?? negar o condicionar el uso de los <b>MEDIOS DE DISPOSICI??N</b> por razones de g??nero, nacionalidad, ??tnicas, preferencia sexual, religiosa o cualquier otra particularidad.
                                            <br> <br>
                                            <b>S??PTIMA. ROBO O EXTRAV??O DEL MEDIO DE DISPOSICI??N O DEFUNCI??N DEL ACREDITADO</b>. En caso de robo o extrav??o de cualesquiera de sus <b>MEDIOS DE DISPOSICI??N</b>, el <b>ACREDITADO</b> deber?? notificarlo telef??nicamente de forma inmediata a la Unidad Especializada de Atenci??n a Usuarios de la <b>SOFOM</b> cuyo tel??fono aparecer?? en la <b>TARJETA F??SICA</b> y/o en la <b>APLICACI??N</b>, debiendo ratificar por escrito dicha situaci??n a la <b>SOFOM</b>, presentando su escrito en el domicilio de la <b>SOFOM</b> dentro de las 24 (veinticuatro) horas siguientes a dicha notificaci??n.
                                            </p>
                                            """.trimMargin()
                                    }
                                    unsafe {
                                        +"""
                                            <p>
                                            Para todas las disposiciones ocurridas previo al reporte telef??nico en el que se le haya otorgado la clave o folio de reporte, el <b>ACREDITADO</b> ser?? responsable, sin restricci??n ni condici??n alguna, de las disposiciones realizadas con cargo a su <b>L??NEA DE CR??DITO</b> mediante el uso del <b>MEDIO DE DISPOSICI??N</b> siendo igualmente responsable de los cargos e intereses ordinarios y moratorios que por tales disposiciones se generen en los t??rminos previstos en este Contrato.
                                            <br> <br>
                                            En caso de defunci??n del <b>ACREDITADO</b>, el presente Contrato dejar?? de surtir efectos salvo por aquellos saldos insolutos que deber??n de ser cubiertos por la sucesi??n del mismo. En caso de defunci??n, deber?? de notificarse a la <b>SOFOM</b> en los mismos t??rminos indicados en el p??rrafo anterior, en el entendido de que dicha notificaci??n deber?? ser realizada por el albacea de la sucesi??n, o cualquier persona con inter??s jur??dico, o el titular de la <b>TARJETA</b> adicional, en caso de haberlo. En todo caso, la sucesi??n del <b>ACREDITADO</b> y solidariamente el titular de la <b>TARJETA</b> adicional, en caso de haberlo, ser??n responsables ??nicamente por los cargos efectuados con el <b>MEDIO DE DISPOSICI??N</b> y/o <b>TARJETA</b> adicional con posterioridad a la notificaci??n de la defunci??n del <b>ACREDITADO</b>.
                                            <br> <br>
                                            <b>OCTAVA. ESTADO DE CUENTA</b>. La <b>SOFOM</b> enviar?? al <b>ACREDITADO</b> un estado de cuenta mensual, en el que se informar??n los movimientos realizados y generados en cada periodo de su cuenta, as?? como la indicaci??n del plazo para su pago. Lo anterior s??lo en caso de que se registre un saldo o movimiento en la cuenta del <b>ACREDITADO</b>. Dicho estado de cuenta ser?? enviado dentro de los 10 (diez) d??as siguientes al corte de la cuenta. El estado de cuenta ser?? enviado por medios electr??nicos y/o por correo al domicilio que el <b>ACREDITADO</b> se??al?? en la <b>SOLICITUD</b>, por lo que cualquier cambio de domicilio deber?? ser notificado por el <b>ACREDITADO</b> a la <b>SOFOM</b> al tel??fono se??alado en el estado de cuenta o por cualquier otro medio que la <b>SOFOM</b> le indique, a m??s tardar con 15 (quince) d??as naturales de anticipaci??n a la fecha de corte de su cr??dito; de lo contrario, se considerar?? v??lidamente enviado el estado de cuenta al <b>ACREDITADO</b>. No obstante lo anterior, la <b>SOFOM</b> podr?? enviar al <b>ACREDITADO</b> el estado de cuenta a trav??s de medios electr??nicos, siempre y cuando el <b>ACREDITADO</b> as?? lo autorice, en este supuesto, el <b>ACREDITADO</b> ser?? responsable de consultar el estado de cuenta por los medios pactados.
                                            <br> <br>
                                            El <b>ACREDITADO</b> dispondr?? de un plazo de 90 (noventa) d??as naturales contados a partir de su fecha de corte, que ser?? el d??a que al efecto se se??ale en el estado de cuenta de manera mensual, para objetar por escrito dicho estado de cuenta. Transcurrido el plazo mencionado sin haber presentado objeci??n al estado de cuenta, se entender?? que el <b>ACREDITADO</b> acepta los t??rminos del mismo.
                                            <br> <br>
                                            El presente Contrato acompa??ado de la certificaci??n del contador de la <b>SOFOM</b> respecto del estado de cuenta, constituir??n t??tulo ejecutivo mercantil, sin necesidad de reconocimiento de firma ni de otro requisito alguno.
                                            <br> <br>
                                            En todo momento el <b>ACREDITADO</b> podr?? solicitar telef??nicamente al Centro de Atenci??n Telef??nica, su estado de cuenta y saldo pendiente de pago o, en su defecto, la informaci??n contenida en su estado de cuenta.
                                            <br> <br>
                                            Tomando en consideraci??n la facultad que le concede la <b>SOFOM</b> al <b>ACREDITADO</b>, para exigir en cualquier tiempo la informaci??n sobre el importe del saldo, el <b>ACREDITADO</b> se obliga a pagar a m??s tardar a los 10 (diez) d??as h??biles siguientes a la fecha de corte, el saldo de su <b>L??NEA DE CR??DITO</b>, a??n y cuando por cualquier raz??n no haya recibido su estado de cuenta.]
                                            <br> <br>
                                            <b>NOVENA. PAGOS</b>. La fecha l??mite de pago ser?? la que determine el estado de cuenta que la <b>SOFOM</b> entregue al <b>ACREDITADO</b>. En caso que la fecha l??mite de pago sea un d??a inh??bil bancario, el pago respectivo podr?? realizarse el siguiente d??a h??bil bancario, en cuyo caso el <b>ACREDITADO</b> no pagara?? intereses moratorios a la <b>SOFOM</b>.
                                            <br> <br>
                                            Los pagos deber??n efectuarse en la red de comisionistas, en equipos automatizados, sistemas telef??nicos y/o electr??nicos en moneda nacional, salvo que la <b>SOFOM</b> notifique al <b>ACREDITADO</b> cualquier otro medio de pago.
                                            <br> <br>
                                            El <b>ACREDITADO</b> pagar?? como Pago M??nimo, la cantidad que resulte m??s alta de los puntos siguientes:
                                            <br> <br>
                                            La suma de 1.5% (uno punto cinco por ciento) del saldo insoluto de la parte revolvente del importe del periodo correspondiente (???<b>CICLO</b>???), sin contar los intereses del periodo ni el <b>IVA</b>, m??s los referidos intereses y el <b>IVA</b>.
                                            <br> <br>
                                            El 1.25% (uno punto veinticinco por ciento) del <b>L??MITE DE CR??DITO</b>.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            La <b>SOFOM</b> podr?? determinar libremente el importe del Pago M??nimo, siempre y cuando dicho importe sea mayor al de los incisos a) y b), en este caso, el <b>ACREDITADO</b> pagar?? como Pago M??nimo a la <b>SOFOM</b> la cantidad correspondiente se??alada en el estado de cuenta, incluyendo intereses ordinarios, intereses moratorios, impuestos y comisiones. El Pago M??nimo ser?? recalculado en cada fecha de corte. No obstante, en caso de que el resultado de dicha operaci??n sea menor a $200.00 (doscientos pesos 00/100 M.N.), el <b>ACREDITADO</b> deber?? pagar como Pago M??nimo la cantidad de $200.00 (doscientos pesos 00/100 M.N.). Para efectos del c??lculo anterior, se entender?? como saldo al corte la cantidad que resulte de sumar: (i) el importe de los cargos realizados en el Per??odo Mensual de que se trate, m??s; (ii) el saldo deudor de periodos anteriores, para el caso de que aplique; m??s (iii) los intereses ordinarios que en su caso se hayan devengado, m??s; (iv) las comisiones que apliquen y los intereses moratorios que procedan de acuerdo a lo establecido en el presente Contrato. En caso de que el saldo al corte sea menor a $200.00 (doscientos pesos 00/100 M.N.), no se calcular?? el Pago M??nimo y se deber?? liquidar el saldo total. 
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            En caso de promociones ofrecidas por la <b>SOFOM</b>, aplicar??n: i) el tipo de cr??dito (simple o cuenta corriente), ii) el programa (meses con o sin intereses), iii) la tasa de inter??s (ordinaria o moratoria), y/o iv) el c??lculo indicado en cada promoci??n.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Para los efectos de la presente cl??usula se entender?? por Periodo Mensual los d??as que transcurran entre el d??a siguiente a la fecha de corte del mes inmediato anterior y la fecha de corte del mes inmediato siguiente.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Si el importe del saldo insoluto de la cuenta excede al <b>L??MITE DE CR??DITO</b>, el excedente deber???? cubrirse de inmediato por el <b>ACREDITADO</b>.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            <b>D??CIMA. PAGOS ANTICIPADOS Y PAGOS ADELANTADOS</b>. El <b>ACREDITADO</b> podr?? realizar pagos adelantados a la fecha de vencimiento del pago sin penalizaci??n o comisi??n alguna, debiendo realizarse por un importe igual o mayor a la amortizaci??n que corresponda, siempre y cuando el <b>ACREDITADO</b> se encuentre al corriente en el pago del cr??dito, intereses y sus accesorios, debiendo dar aviso a la <b>SOFOM</b> previo a la fecha en que se realice el pago a trav??s de cualquiera de los medios que le sean especificados de tiempo en tiempo por esta en t??rminos de lo dispuesto por la cl??usula Vig??sima Cuarta. 
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Los pagos adelantados que realice el <b>ACREDITADO</b> que no alcancen a cubrir el saldo total, se aplicar??n a las amortizaciones peri??dicas subsecuentes, reduciendo el monto a pagar de la amortizaci??n que corresponda o en su caso se reducir?? el n??mero de semanas, quincenas o meses de pago del cr??dito, previo acuerdo con el <b>ACREDITADO</b>.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Los pagos adelantados parciales realizados por el <b>ACREDITADO</b> se ver??n reflejados en el estado de cuenta del per??odo en que se efectu?? el pago adelantado parcial correspondiente y ser??n aplicados de acuerdo a la prelaci??n establecida.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            El pago total del cr??dito podr?? ser efectuado por el <b>ACREDITADO</b> mediante el pago anticipado correspondiente. Una vez efectuado el pago total del cr??dito a entera satisfacci??n de la <b>SOFOM</b>, ??sta a solicitud del <b>ACREDITADO</b> podr?? poner a su disposici??n un documento que ampare el finiquito total de la operaci??n.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            <b>D??CIMA PRIMERA. APLICACI??N DE LOS PAGOS</b>. Los pagos deber??n realizarse en moneda nacional, en efectivo o por cualquier otro medio que la <b>SOFOM</b> le informe al <b>ACREDITADO</b>, a trav??s de cualquiera de los medios autorizados por la <b>SOFOM</b> en t??rminos de este Contrato y que de tiempo en tiempo se hagan del conocimiento del <b>ACREDITADO</b> en el estado de cuenta.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            El <b>ACREDITADO</b> acuerda con la <b>SOFOM</b> aplicar las cantidades que aquel pague, en el siguiente orden: (i) saldo vencido (ii) saldo vigente. Se entiende por saldo vencido, en este orden, (i) los impuestos, (ii) comisiones, (iii) intereses y capital derivados de las disposiciones del cr??dito y no cubiertos en tiempo; y por saldo vigente, en este orden: (i) los impuestos, (ii) comisiones), intereses y capital derivados de las disposiciones del cr??dito que se encuentren vigentes.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            La aplicaci??n de pagos mencionada en el p??rrafo anterior ser?? modificada cuando el <b>ACREDITADO</b> estando al corriente efect??e pagos superiores al saldo deudor, en el entendido que el excedente podr?? (i) ser aplicado al saldo vigente en el periodo o (ii) ser dispuesto por el <b>ACREDITADO</b> a trav??s de las formas de disposici??n establecidas en la cl??usula Sexta del presente Contrato, en el entendido que la <b>L??NEA DE CR??DITO</b> otorgada no constituye un medio de ahorro, ni un medio para realizar transacciones a otras cuentas, es decir, es solo un medio de pago, por lo que, si despu??s de compensados todos los saldos, existiera un saldo a favor, este no generar?? intereses en favor del <b>ACREDITADO</b> incluso quedando reservada para la SOFOM la facultad de dar por terminado y/o cancelar la <b>L??NEA DE CR??DITO</b> correspondiente cuando el <b>ACREDITADO</b> incurra reiteradamente en este tipo de pr??cticas.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Debido a que la <b>L??NEA DE CR??DITO</b> materia del presente Contrato, no constituye un medio de ahorro, los saldos de los pagos realizados en exceso por el <b>ACREDITADO</b> no generar??n rendimientos a su favor.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            En caso de promociones, el pago se aplicar?? al capital con la siguiente prelaci??n: en primer lugar se liquidan las promociones sin intereses con mayor n??mero de parcialidades vencidas y en ??ltimo lugar las promociones con intereses con mayor n??mero de parcialidades vencidas.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Adicionalmente, en caso de tener saldo pendiente de pago, el <b>ACREDITADO</b> deber?? cubrir el total del Pago M??nimo requerido en su estado de cuenta para permanecer en las diferentes promociones vigentes. Los pagos se aplicar??n primero a las promociones especiales y por ??ltimo al plan de pagos indicado en la car??tula de cr??dito. En caso de incumplimiento del Pago M??nimo, el <b>ACREDITADO</b> perder?? los beneficios de las promociones aplicando al total del saldo los intereses ordinarios indicados en la car??tula.
                                            <br> <br>
                                            <b>D??CIMA SEGUNDA. INTERESES</b>. El inter??s ordinario referido en el presente Contrato ser?? determinado anualmente aplicando la tasa de inter??s ordinaria fija referida en el <b>ANEXO</b> B del presente Contrato al saldo promedio diario no pagado que tenga el <b>ACREDITADO</b>; dividiendo el resultado obtenido entre 12 (doce) meses, la tasa resultante se multiplicar?? por el saldo del capital, el que por concepto de intereses ordinarios deber?? pagar el <b>ACREDITADO</b> en la fecha que corresponda. Los intereses podr??n capitalizarse. Su pago se exigir?? por periodos vencidos.
                                            <br> <br>
                                            En caso de incumplimiento de pago o pago tard??o conforme a los t??rminos del presente Contrato, se cobrar?? un inter??s moratorio al <b>ACREDITADO</b> determinado anualmente aplicando la tasa de inter??s moratoria fija referida en el <b>ANEXO</b> B del presente Contrato, calculada diariamente sobre el capital vencido y no pagado en los t??rminos indicados en el referido <b>ANEXO B</b> a partir del primer d??a despu??s de la fecha de incumplimiento y hasta el d??a en que se efect??e el pago total del adeudo. En el entendido que el cobro de intereses moratorios elimina o sustituye el cobro de comisi??n por pago tard??o. 
                                            <br> <br>
                                            <b>D??CIMA TERCERA. CARGOS Y COMISIONES</b>. El concepto, monto y periodicidad de pago de los cargos, intereses y comisiones aplicables al presente Contrato se indican en el <b>ANEXO B</b> del mismo, los cuales tambi??n podr??n ser consultados de tiempo en tiempo en <a href="https://karum.com/operadora-activacel" target="_blank">https://karum.com/operadora-activacel</a>. El <b>ACREDITADO</b> se obliga a pagar dichos cargos, intereses y comisiones m??s su respectivo IVA, sin necesidad de previo requerimiento por los medios que la <b>SOFOM</b> ponga a disposici??n del <b>ACREDITADO</b>. 
                                            <br> <br>
                                            La <b>SOFOM</b> no podr?? cobrar comisiones por conceptos distintos a los se??alados en el <b>ANEXO B</b>. Durante la vigencia del presente Contrato la <b>SOFOM</b> podr?? modificar y/o actualizar los cargos, intereses y comisiones debiendo notificar previamente de manera expresa, mediante el estado de cuenta, correo electr??nico, telef??nicamente o bien, por cualquier medio electr??nico al <b>ACREDITADO</b> los nuevos t??rminos establecidos, con una anticipaci??n de cuando menos 30 (treinta) d??as naturales a la fecha en que entren en vigor, se??alando espec??ficamente esta ??ltima. El <b>ACREDITADO</b> acepta expresamente que se entender?? que otorga su consentimiento a las nuevas cuotas que la <b>SOFOM</b> le notifique, si este hiciere uso de la <b>L??NEA DE CR??DITO</b> posterior a la notificaci??n, o bien si no manifestare expresamente su objeci??n a las mismas dentro de los 30 (treinta) d??as naturales siguientes a la fecha en que entren en vigor.
                                            <br> <br>
                                            <b>D??CIMA CUARTA. PROGRAMAS (CR??DITO SIMPLE O CUENTA CORRIENTE) Y PROMOCIONES (CON INTERESES O PAGO DIFERIDO)</b>. Es la opci??n que tiene el <b>ACREDITADO</b> para solicitar a la <b>SOFOM</b>, a trav??s del Centro de Atenci??n a Clientes o de los medios electr??nicos que ??ste ??ltimo ponga a su disposici??n, que respecto de una parte o el total de su saldo deudor se apliquen condiciones de pago distintas en cuanto a tipo de cr??dito, plazo y tasa de inter??s. El tipo de cr??dito, plazo y tasa de inter??s aplicables ser??n informados al <b>ACREDITADO</b> por la <b>SOFOM</b> al momento de la solicitud que aquel realice por los medios pactados. Esta solicitud estar?? sujeta a la aprobaci??n de la <b>SOFOM</b>.
                                            <br> <br>
                                            La solicitud con el tipo de cr??dito, plazo y tasa de inter??s aplicables, as?? como la vigencia de la misma, se establecer?? en el documento denominado Detalles del Cr??dito (ANEXO D). La <b>SOFOM</b> podr?? dar por terminada la vigencia de la promoci??n en cualquier momento dando aviso al <b>ACREDITADO</b> a trav??s de los medios de comunicaci??n se??alados en el presente Contrato.
                                            <br> <br>
                                            <b>D??CIMA QUINTA. TASA DE INTER??S PROMOCIONAL</b>. Es la tasa de inter??s que la <b>SOFOM</b> podr?? poner a disposici??n del <b>ACREDITADO</b> como resultado de campa??as promocionales que le ser??n informadas al <b>ACREDITADO</b> de manera escrita, mediante el estado de cuenta, correo electr??nico, telef??nicamente o bien, por cualquier otro medio electr??nico.
                                            <br> <br>
                                            <b>D??CIMA SEXTA. CARGOS A LA CUENTA Y AUTORIZACI??N DE CARGOS RECURRENTES</b>. La <b>SOFOM</b> cargar?? a la cuenta los conceptos que se mencionan a continuaci??n, los cuales, el <b>ACREDITADO</b> se obliga a pagar a la <b>SOFOM</b>, a trav??s de la red de comisionistas, en equipos automatizados, sistemas telef??nicos y/o electr??nicos en moneda nacional y/o en efectivo, salvo que la <b>SOFOM</b> notifique al <b>ACREDITADO</b> cualquier otro medio de pago, sin necesidad de requerimiento previo:
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            a)	El importe de los pagos de bienes, servicios, impuestos y dem??s conceptos que realice la <b>SOFOM</b> por cuenta del <b>ACREDITADO</b> o de las personas a quien ??ste ??ltimo otorgue <b>TARJETAS</b> adicionales, cuando el <b>ACREDITADO</b> y ??stos ??ltimos: (i) hayan suscrito pagar??s u otros documentos que sean aceptados por la <b>SOFOM</b> y se hayan entregado al establecimiento respectivo, (ii) los hayan autorizado a trav??s de cualquier medio, o (iii) hayan solicitado por v??a telef??nica o electr??nica a <b>LOS ESTABLECIMIENTOS</b> la compra de bienes o servicios. 
                                            <br> <br>
                                            b)	El importe de los pagos de bienes, servicios, impuestos y dem??s conceptos que realice la <b>SOFOM</b> por cuenta del <b>ACREDITADO</b>, por concepto de cargos recurrentes que hayan sido contratados y autorizados por el <b>ACREDITADO</b> directamente con <b>LOS ESTABLECIMIENTOS</b>, a trav??s de cualquier medio, incluyendo v??a telef??nica y/o electr??nica. En todo caso el <b>ACREDITADO</b> deber?? de cancelar las autorizaciones que hubiere efectuado para la contrataci??n de cargos recurrentes de forma directa con los propios establecimientos y/o comercios con quienes los haya contratado, salvo que la <b>SOFOM</b> le informe de otro medio para realizarlo.
                                            <br> <br>
                                            c)	Las disposiciones en efectivo de su <b>L??NEA DE CR??DITO</b> hechas en establecimientos afiliados o a trav??s de otros medios automatizados o electr??nicos que al efecto tenga establecidos o se pacten con la <b>SOFOM</b>, en el territorio nacional; dentro de las disposiciones de efectivo se comprender??n aquellas cantidades cargadas como comisiones a fin de obtener la cantidad dispuesta por parte del <b>ACREDITADO</b>. 
                                            <br> <br>
                                            d)	Los intereses pactados.
                                            <br> <br>
                                            e)	Las comisiones que al efecto la <b>SOFOM</b> tenga establecidas en los t??rminos de la cl??usula denominada <b>CARGOS Y COMISIONES</b> de este Contrato. 
                                            <br> <br>
                                            f)	El <b>IVA</b> o cualquier otro impuesto que establezcan las leyes respectivas. 
                                            <br> <br>
                                            g)	Cualquier otro importe que se genere a cargo del <b>ACREDITADO</b>, en virtud de este Contrato.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            <b>D??CIMA S??PTIMA. ANEXOS</b>. Los siguientes anexos forman parte del Contrato, por lo cual se deber??n considerar como parte integrante del mismo:
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            <b>Anexo A.</b> Solicitud de cr??dito. <br>
                                            <b>Anexo B.</b> Car??tula de cr??dito. <br>
                                            <b>Anexo C.</b> Referencias legales. <br>
                                            <b>Anexo D.</b> Detalles del cr??dito.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            <b>D??CIMA OCTAVA. INCUMPLIMIENTO POR MORA</b>. En caso de que el <b>ACREDITADO</b> no cumpla con el Pago M??nimo que haya acordado con la <b>SOFOM</b> dentro del periodo pactado en el presente, estar?? obligado a pagar adicionalmente una comisi??n por pago tard??o, determinada conforme a lo referido en el <b>ANEXO B</b> del Contrato. No se cobrar?? dicha comisi??n cuando se cobren intereses moratorios durante el mismo periodo.
                                            <br> <br>
                                            En caso de incumplimiento de tres o m??s pagos m??nimos consecutivos, la <b>SOFOM</b> podr?? dar por vencido anticipadamente el cr??dito, exigir el pago correspondiente del saldo total a la fecha de terminaci??n.
                                            <br> <br>
                                            La omisi??n por parte de la <b>SOFOM</b> en el ejercicio de los derechos que deriven a su favor del presente Contrato, en ning??n caso deber?? entenderse o tendr??n el efecto de considerarse como una renuncia a los mismos, as?? como el ejercicio parcial de un derecho derivado de este Contrato por parte de la <b>SOFOM</b> no excluye la posibilidad de ejercer alg??n otro derecho o facultad.
                                            <br> <br>
                                            <b>D??CIMA NOVENA. CARACTER??STICAS DEL CR??DITO</b>. Las caracter??sticas de la operaci??n que constan en este Contrato se precisan en el <b>ANEXO B</b> (car??tula de cr??dito), el cual forma parte integrante del presente instrumento.
                                            <br> <br>
                                            <b>VIG??SIMA. COSTO ANUAL TOTAL</b>. El Costo Anual Total (???<b>CAT</b>???) se reflejar?? en el estado de cuenta a que se refiere la cl??usula Octava. El <b>CAT</b> est?? expresado en t??rminos porcentuales anuales que, para fines informativos y de comparaci??n, incorpora la totalidad de los costos y gastos inherentes a los cr??ditos. El <b>CAT</b> ha sido precisado con fines de referencia en el <b>ANEXO B</b> del presente Contrato.
                                            <br> <br>
                                            <b>VIG??SIMA PRIMERA. CESI??N DE CR??DITO</b>. El <b>ACREDITADO</b> faculta expresamente a la <b>SOFOM</b> para ceder o descontar el cr??dito del presente Contrato, as?? como los derechos y obligaciones derivados del mismo, sin necesidad de autorizaci??n del <b>ACREDITADO</b>. 
                                            <br> <br>
                                            El <b>ACREDITADO</b> no podr?? ceder los derechos y obligaciones derivadas del presente instrumento.
                                            <br> <br>
                                            <b>VIG??SIMA SEGUNDA. VIGENCIA, TERMINACI??N ANTICIPADA O RESCISI??N DEL CONTRATO</b>. La duraci??n de este Contrato ser?? indefinida. Cualquiera de las Partes podr?? dar por terminado este acuerdo, en cualquier momento, notificando previamente en los t??rminos establecidos en esta cl??usula.
                                            <br> <br>
                                            El <b>ACREDITADO</b> podr?? solicitar en todo momento la terminaci??n del presente Contrato, bastando para ello la presentaci??n de una solicitud por escrito en el domicilio de la <b>SOFOM</b>, o mediante una llamada telef??nica al Centro de Atenci??n Telef??nica, siempre y cuando el <b>ACREDITADO</b> haya liquidado previamente la totalidad de los adeudos, tanto accesorios como principales, otorgando la <b>SOFOM</b> al <b>ACREDITADO</b> un acuse o c??digo de seguimiento sobre el tr??mite de terminaci??n.
                                            <br> <br>
                                            Para tales efectos, la <b>SOFOM</b> deber?? comunicar al <b>ACREDITADO</b> el importe de los adeudos, a m??s tardar el d??a h??bil siguiente a la fecha de solicitud de terminaci??n del Contrato; dicha informaci??n estar?? a disposici??n del <b>ACREDITADO</b> en el domicilio de la <b>SOFOM</b> dentro de los 5 (cinco) d??as siguientes a la solicitud de terminaci??n. 
                                            <br> <br>
                                            Una vez ocurrido lo anterior, previa entrega y revisi??n del estado de cuenta a la fecha de la solicitud de terminaci??n del <b>ACREDITADO</b>, la <b>SOFOM</b> se sujetar?? a lo siguiente:
                                            <br> <br>
                                            El presente Contrato se dar?? por terminado el d??a h??bil siguiente al de la acreditaci??n del pago ??ntegro de los adeudos generados por el cr??dito otorgado y la presentaci??n de la solicitud de terminaci??n por parte del <b>ACREDITADO</b>. Una vez autorizada la solicitud de terminaci??n del <b>ACREDITADO</b>, la <b>SOFOM</b> inhabilitar?? la <b>L??NEA DE CR??DITO</b> y no se podr?? realizar m??s disposiciones por ning??n medio. 
                                            <br> <br>
                                            En la fecha que se d?? por terminado el Contrato, la <b>SOFOM</b> deber?? entregar al <b>ACREDITADO</b> cualquier saldo que ??ste tenga a su favor, deduciendo en su caso, las comisiones y cualquier otra cantidad que, en t??rminos del presente Contrato, puedan resultar a cargo del <b>ACREDITADO</b>, mediante la habilitaci??n de una ??ltima operaci??n en su <b>TARJETA FISICA</b>, consistente en el retiro de la cantidad de dinero en efectivo que corresponda reintegrarle. Esta ??ltima operaci??n no ser?? considerada como una disposici??n de su <b>L??NEA DE CR??DITO</b> sino una devoluci??n de un saldo a favor. 
                                            <br> <br>
                                            Realizado el pago y liquidado todo adeudo que tenga el <b>ACREDITADO</b>, la <b>SOFOM</b> deber?? poner a su disposici??n un documento, o bien un estado de cuenta, que d?? constancia del fin de la relaci??n contractual y de la inexistencia de adeudos entre las Partes. La <b>SOFOM</b> una vez que se hubiere agotado el procedimiento previsto en este art??culo, no podr?? efectuar al <b>ACREDITADO</b> requerimiento de pago alguno.
                                            <br> <br>
                                            Si el <b>ACREDITADO</b> incumple con cualquier obligaci??n a su cargo prevista en el presente instrumento, el saldo total del cr??dito ser?? exigible inmediatamente, incluyendo comisiones e intereses ordinarios y moratorios. Es causa de rescisi??n del presente Contrato el incumplimiento de cualquier obligaci??n pactada en el mismo.
                                            <br> <br>
                                            El mismo procedimiento descrito en esta cl??usula se seguir?? si la <b>SOFOM</b> denuncia el presente Contrato para el monto del cr??dito que el <b>ACREDITADO</b> no hubiere utilizado en dicho momento.
                                            <br> <br>
                                            La <b>SOFOM</b> tendr?? derecho a dar por terminado el Contrato en cualquier momento sin responsabilidad adicional que la de dar aviso al <b>ACREDITADO</b> mediante comunicaci??n escrita, el estado de cuenta, correo electr??nico, telef??nicamente o bien, por cualquier otro medio electr??nico. La terminaci??n referida en este p??rrafo surtir?? efectos al d??a siguiente de su notificaci??n; no obstante, continuar??n surtiendo efectos los t??rminos aplicables al saldo pendiente de pago por parte del <b>ACREDITADO</b> al momento de la terminaci??n, hasta que se liquide la totalidad del saldo adeudado. 
                                            <br> <br>
                                            A la terminaci??n del Contrato, el <b>ACREDITADO</b> deber?? devolver inmediatamente a la <b>SOFOM</b> las <b>TARJETAS F??SICAS</b> que le hubieren sido entregadas con motivo de este Contrato, esto sin perjuicio del derecho de cobro que tiene la <b>SOFOM</b> respecto del <b>ACREDITADO</b>, en los tiempos y plazos acordados en este instrumento.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Consecuentemente, se extinguir?? el cr??dito en la parte que el <b>ACREDITADO</b> no hubiere hecho efectiva.
                                            <br> <br>
                                            <b>VIG??SIMA TERCERA. MODIFICACIONES AL CONTRATO</b>. La <b>SOFOM</b> podr?? modificar el presente Contrato parcial o totalmente, inform??ndole por escrito al <b>ACREDITADO</b> las modificaciones, con 30 (treinta) d??as naturales de anticipaci??n a su entrada en vigor, por medios electr??nicos y/o por cualquier otro medio que establezcan las Partes. En caso de que se modifiquen las condiciones generales y/o tasas de inter??s del presente Contrato, las nuevas condiciones ser??n dadas a conocer al <b>ACREDITADO</b> en comunicaci??n escrita o por medios electr??nicos y por cualquier otro medio que establezcan las Partes, bastando la anotaci??n que al respecto se haga en el estado de cuenta referido en la cl??usula Octava, salvo en el caso de reestructura del cr??dito, para ello, la <b>SOFOM</b> notificar?? y explicar?? al <b>ACREDITADO</b> de forma fehaciente la manera en que operar?? la reestructura y a su vez obtendr?? su consentimiento expreso.
                                            <br> <br>
                                            El <b>ACREDITADO</b> acepta expresamente que se entender?? que otorga su consentimiento a las modificaciones al Contrato que la <b>SOFOM</b> le notifique, si este hiciere uso de la <b>L??NEA DE CR??DITO</b> posterior a la notificaci??n, o bien si no manifestare expresamente su objeci??n a las mismas dentro de los 30 (treinta) d??as naturales siguientes a la fecha en que entren en vigor. En caso de que el <b>ACREDITADO</b> no est?? de acuerdo con las modificaciones propuestas, podr?? solicitar la terminaci??n del presente Contrato hasta 60 (sesenta) d??as naturales despu??s de la entrada en vigor de dichas modificaciones, sin responsabilidad alguna a su cargo, debiendo cubrir, en su caso, los adeudos que ya se hubieren generado. En este caso la <b>SOFOM</b> no podr?? cobrar cantidad adicional alguna por la terminaci??n de la prestaci??n de los servicios, con excepci??n de los adeudos que ya se hubieren generado a la fecha en que el <b>ACREDITADO</b> solicite dar por terminado el servicio.
                                            <br> <br>
                                            Se tendr?? como el contrato de adhesi??n v??lido entre las Partes, el vigente registrado ante la <b>CONDUSEF</b> en el Registro de Contratos de Adhesi??n del producto respectivo. Lo anterior salvo que dicho contrato de adhesi??n sea dado de baja en el referido registro por dejar de comercializarse el producto, en cuyo caso, se considerar??n los avisos de las modificaciones que el <b>ACREDITADO</b> haya aceptado para determinar la versi??n vigente del mismo.
                                            <br> <br>
                                            El <b>ACREDITADO</b> reconoce y est?? de acuerdo en que la <b>SOFOM</b> podr?? libremente y sin limitaci??n alguna, realizar todas las modificaciones t??cnicas, f??sicas, mec??nicas o de cualquier otra naturaleza, que sean necesarias para mejorar, actualizar o suprimir algunas de las funciones de los medios electr??nicos incluyendo la <b>APLICACI??N</b>, as?? mismo, podr?? llevar a cabo la eliminaci??n de algunas operaciones electr??nicas o la eliminaci??n total de algunos de los medios electr??nicos, seg??n la <b>SOFOM</b> lo considere necesario.
                                            <br> <br>
                                            <b>VIG??SIMA CUARTA. DOMICILIOS Y NOTIFICACIONES</b>. Para todos los efectos judiciales y extrajudiciales, el <b>ACREDITADO</b> se??ala como medio para recibir y o??r todo tipo de notificaciones la <b>APLICACI??N</b> y/o su domicilio indicado en la <b>SOLICITUD</b> que como <b>ANEXO</b> A se agrega a este Contrato. Por lo tanto, las personas que se encuentren en dicho domicilio se entienden como expresamente autorizadas por el <b>ACREDITADO</b> para recibir todo tipo de avisos y previa identificaci??n podr??n recibir los <b>MEDIO DE DISPOSICI??N</b> del <b>ACREDITADO</b> objeto del presente Contrato.
                                            <br> <br>
                                            La <b>SOFOM</b> se??ala como domicilio convencional el ubicado en Blvd. Manuel ??vila Camacho No. 5, Interior S 1000, Edificio Torre B, Piso 10, Oficina 1045, Col. Lomas de Sotelo, Naucalpan de Ju??rez, Estado de M??xico. CP 53390.
                                            <br> <br>
                                            Las Partes expresan su conformidad para que las notificaciones sobre cualquier asunto relacionado con el presente Contrato, en tanto las cl??usulas espec??ficas no se??alen una tramitaci??n especial, sean mediante simple comunicaci??n escrita, la cual podr?? ser entregada por los medios electr??nicos acordados con el <b>ACREDITADO</b>.  
                                            <br> <br>
                                            <b>VIG??SIMA QUINTA. VERIFICACI??N DE DATOS</b>. El <b>ACREDITADO</b> autoriza expresamente a la <b>SOFOM</b> para que por s?? misma o a trav??s de prestadores de servicios y sociedades especializadas, corrobore los datos aqu?? asentados e investigue su historial crediticio, incluso autoriza a que le llamen telef??nicamente para dichos efectos y especialmente autoriza a que la <b>SOFOM</b> pueda proporcionar a cualesquiera sociedades de informaci??n crediticia su comportamiento crediticio. Lo anterior lo autoriza mediante la firma del documento que se agrega al presente instrumento formando parte integrante del mismo como <b>ANEXO A</b>. 
                                            <br> <br>
                                            De tiempo en tiempo la <b>SOFOM</b> podr?? monitorear llamadas telef??nicas realizadas entre el <b>ACREDITADO</b> y/o titulares de <b>TARJETAS</b> adicionales y la <b>SOFOM</b> con la finalidad de asegurar la calidad de servicio al cliente. El <b>ACREDITADO</b> ser?? avisado por parte de la <b>SOFOM</b> del monitoreo de la llamada telef??nica. 
                                            <br> <br>
                                            <b>VIG??SIMA SEXTA. PREVENCI??N DE FRAUDES</b>. La <b>SOFOM</b> no solicitar?? al <b>ACREDITADO</b> por ning??n medio y bajo ninguna circunstancia pagos o dep??sitos a cuentas de terceros que no se encuentren bajo la titularidad de la <b>SOFOM</b> para otorgar cr??ditos, as?? como tampoco solicitar?? el n??mero de identificaci??n personal <b>NIP</b> para ninguna consulta, modificaci??n o aclaraci??n.
                                            <br> <br>
                                            <b>VIG??SIMA S??PTIMA. INFORMACI??N</b>. El <b>ACREDITADO</b> autoriza a la <b>SOFOM</b> a:
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            a)	Proporcionar informaci??n que se estime pertinente a quien preste los servicios operativos y de maquila de la <b>TARJETA</b> o <b>MEDIO DE DISPOSICI??N</b> que corresponda.
                                            <br> <br>
                                            b)	Utilizar la informaci??n que le haya proporcionado en la <b>SOLICITUD</b> y de la operaci??n del cr??dito para actividades promocionales o para ofrecer operaciones y servicios de cualquier empresa subsidiaria, filial o controlada directa o indirectamente por la <b>SOFOM</b>.
                                            <br> <br>
                                            c)	Verificar su Credencial para Votar ante el Instituto Nacional Electoral (en lo sucesivo ???INE???); en ese contexto, el <b>ACREDITADO</b> reconoce y acepta que en el caso de haber otorgado su consentimiento expreso por medios electr??nicos, a trav??s de los mecanismos que la <b>SOFOM</b> tenga habilitados y/o llegue a habilitar para tal efecto en la <b>APLICACI??N</b> y/o a trav??s de su portal, la <b>SOFOM</b>, podr?? hacer uso de sus datos personales como la entidad federativa, municipio y localidad que corresponden a su domicilio, secci??n electoral, apellido paterno, apellido materno y nombre completo, y datos personales sensibles como su firma, huella digital y fotograf??a contenidos en su credencial para votar, misma que exhibe de manera digital como medio de autenticaci??n de su identidad, esto a efecto de corroborar que dichos datos coinciden con los que obran en poder del INE, por medio del servicio de verificaci??n de datos de la Credencial para Votar del INE, cuyos principales objetivos son:
                                            <br>
                                            <p style="margin-left:20px;">(i)	Verificar la vigencia y coincidencia de los datos del INE que presenten los ciudadanos para  identificarse ante las instituciones p??blicas y privadas, as?? como las asociaciones civiles, respecto de la informaci??n almacenada en la base de datos del Padr??n Electoral.</p>
                                            <p style="margin-left:20px;">(ii)	Autenticar y/o verificar las huellas dactilares, as?? como informaci??n del ciudadano derivada de sus propias caracter??sticas f??sicas, que sirvan para su identificaci??n mediante la  correlaci??n gr??fica de aquellas capturadas a trav??s de la <b>APLICACI??N</b>, entre s??, y/o con aquellas que se encuentran almacenadas en la base de datos del Padr??n Electoral.</p> 
                                            <span style="color:#182035;">
                                            La <b>SOFOM</b> realizar?? la verificaci??n del <b>INE</b> exclusivamente para efectos de identificaci??n del <b>ACREDITADO</b> y prevenci??n de robo de identidad, por lo que en ning??n caso los datos contenidos en la Credencial para Votar ser??n utilizados para fines publicitarios, de comercializaci??n o distribuci??n       del servicio.
                                            <br> <br>
                                            d)	Proporcionar a las instituciones u organismos relacionados con la administraci??n, operaci??n, y/o manejo de los medios de disposici??n, aquella informaci??n que se estime pertinente y que tenga que ver con el reporte, tratamiento y/o prevenci??n de delitos, il??citos o irregularidades, en el entendido de que cualquier tercero al que sea proporcionada la informaci??n personal del <b>ACREDITADO</b> se sujetar?? a los t??rminos previstos en el Aviso de Privacidad de la <b>SOFOM</b>.
                                            </span>
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            Todos los datos personales recolectados al <b>ACREDITADO</b> se encuentran sujetos al tratamiento previsto en el Aviso de Privacidad simplificado, mismo que se incluye a este Contrato con el <b>ANEXO A</b> y su versi??n integral completa en la p??gina electr??nica <a href="https://karum.com/operadora" target="_blank">https://karum.com/operadora</a>.
                                            <br> <br>
                                            <b>VIG??SIMA OCTAVA. ACEPTACI??N</b>. El <b>ACREDITADO</b> manifiesta que previo a la firma de este Contrato lo ha le??do y afirma que est?? de acuerdo en obligarse conforme a su clausulado, otorgando su consentimiento y aceptaci??n con la Firma Electr??nica que le fue generada a trav??s de la <b>APLICACI??N</b>. Asimismo, acepta tener conocimiento de las comisiones vigentes, <b>CAT</b>, tasas de inter??s, t??rminos y condiciones y que para los efectos de este instrumento los intereses devengados y no pagados tendr??n la consideraci??n de saldo insoluto.
                                            <br> <br>
                                            El <b>ACREDITADO</b> est?? enterado y conoce de las penas y responsabilidades legales en que incurren las personas que al solicitar un cr??dito o para obtenerlo, falsean, alteran u ocultan informaci??n relevante para el otorgamiento o negativa del mismo, por lo que asegura que los datos proporcionados a la <b>SOFOM</b> son verdaderos y correctos.
                                            <br> <br>
                                            EL <b>ACREDITADO</b> contar?? con un per??odo de gracia de 10 (diez) d??as h??biles posteriores a la firma del presente Contrato para cancelarlo sin responsabilidad y sin que se genere comisi??n alguna por este concepto, con independencia de aquellos cargos, cobros o comisiones que hayan ocurrido previo a dicha cancelaci??n. Lo anterior, siempre y cuando el <b>ACREDITADO</b> no haya utilizado u operado los productos o servicios financieros contratados.
                                            <br> <br>
                                            <b>VIG??SIMA NOVENA. EFECTOS DE LA FIRMA ELECTR??NICA</b>. Ambas Partes aceptan que la firma electr??nica sustituir?? la firma aut??grafa del <b>ACREDITADO</b> por una de car??cter electr??nico, por lo que las constancias documentales o t??cnicas en donde aparezca producir??n los mismos efectos que las leyes otorguen a los documentos suscritos con firma aut??grafa, en consecuencia, tendr??n igual valor probatorio.
                                            <br> <br>
                                            El <b>ACREDITADO</b> reconoce el car??cter personal e intransferible de la firma electr??nica, la cual quedar?? bajo su custodia, control y cuidado, por lo que ser?? de la exclusiva responsabilidad del <b>ACREDITADO</b> cualquier da??o o perjuicio que pudiese sufrir como consecuencia del uso indebido de la misma. 
                                            <br> <br>
                                            El <b>ACREDITADO</b> manifiesta que conoce el alcance que en el presente Contrato se le atribuye a la firma electr??nica, por lo que su uso es bajo su estricta responsabilidad. El <b>ACREDITADO</b>, en protecci??n de sus propios intereses, deber?? mantener la firma electr??nica como confidencial, toda vez que el uso de la misma, para todos los efectos legales a que haya lugar, ser?? atribuido al <b>ACREDITADO</b>, a??n y cuando medie caso fortuito o fuerza mayor. 
                                            <br> <br>
                                            Las Partes convienen en que ser??n aplicables, en su momento, los t??rminos del C??digo de Comercio y cualquier otra disposici??n aplicable, respecto de la identidad y expresi??n de consentimiento de las mismas por medios electr??nicos, ??pticos o de cualquier otra tecnolog??a mediante el uso de la firma electr??nica, a fin de que los mensajes de datos sean comunicados entre las Partes de manera segura en su identificaci??n, aut??nticos e ??ntegros en su contenido y no repudiables respecto del emisor y receptor.
                                            </p>
                                            """.trimMargin()
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            <b>TRIG??SIMA. SEGUROS, SERVICIOS Y/O ASISTENCIAS ADICIONALES</b>. Las Partes convienen que, en caso de as?? consentirlo en los t??rminos y condiciones de la <b>APLICACI??N</b>, la <b>SOFOM</b> podr?? ofrecer directamente o a trav??s de terceros, de manera gratuita o con costo, seguros, asistencias y/o servicios adicionales, siempre y cuando cumpla con todos los requisitos establecidos por la <b>SOFOM</b> o prestadores de servicios que en su caso corresponda. Los t??rminos y condiciones de estos seguros, asistencias y/o servicios adicionales, ser??n puestos a disposici??n del <b>ACREDITADO</b> en la p??gina de internet <a href="https://karum.com/operadora" target="_blank">https://karum.com/operadora</a> y/o en la p??gina web de los terceros que presten los servicios, para que puedan ser impresos y consultados en cualquier momento. El ofrecimiento de los seguros, asistencias y/o servicios adicionales que se mencionan en esta cl??usula puede variar, suspenderse o cancelarse en cualquier momento, sin responsabilidad alguna para la <b>SOFOM</b>. Cualquier t??rmino prestaci??n o condici??n de los seguros, asistencias y/o servicios adicionales antes mencionados, ser?? responsabilidad directa de la empresa que presta los servicios de seguros, por lo que la <b>SOFOM</b> no asumir?? responsabilidad alguna en el evento de que el prestador de seguros no llegue a proporcionar los servicios adecuadamente, debiendo en todo caso dirimirse dicha reclamaci??n directamente entre el <b>ACREDITADO</b> y el prestador de servicios de seguros de que se trate, oblig??ndose este ??ltimo a sacar en paz y a salvo a la <b>SOFOM</b> de cualquier controversia relacionada con dichos seguros. La cancelaci??n del (los) seguro(s), asistencia(s) y/o servicio(s) adicional(es) que el <b>ACREDITADO</b> realice, no implicar?? la cancelaci??n o terminaci??n anticipada del presente Contrato.
                                            <br> <br>
                                            <b>TRIG??SIMA PRIMERA. UNIDAD ESPECIALIZADA DE ATENCI??N A USUARIOS, ACLARACIONES Y QUEJAS</b>. El <b>ACREDITADO</b> podr?? notificar, y realizar telef??nicamente cualquier aclaraci??n o consulta, sin costo, de forma inmediata a la Unidad Especializada de Atenci??n a Usuarios, en donde se le proporcionar?? una clave, para que se le d?? el seguimiento correspondiente. Los datos de la Unidad Especializada de Atenci??n a Usuarios son los siguientes:
                                            </p>
                                            """.trimMargin()
                                    }

                                    ul {
                                        style = "color:#182035;"
                                        unsafe {
                                            +"""
                                            <li>
                                            Correo electr??nico UNE: <a href="mailto:atencionaclientes@karum.com">atencionaclientes@karum.com</a>.  
                                            </li>
                                            <li>
                                            Correo electr??nico atenci??n a usuarios: <a href="mailto:atencionaclientes@karum.com">atencionaclientes@karum.com</a>
                                            </li>
                                            <li>
                                            Horario: 09:00 a 18:00 horas.
                                            </li>
                                            <li>
                                            Tel??fono UNE: (55) 8852 8207.
                                            </li>
                                            <li>
                                            Tel??fonos de atenci??n a usuarios: 5568222627.
                                            </li>
                                            <li>
                                            Domicilio: Blvd. Manuel ??vila Camacho No. 5, Interior S 1000, Edificio Torre B, Piso 10, Oficina 1045, Col. Lomas de Sotelo, CP 53390, Naucalpan de Ju??rez, Estado de M??xico.
                                            </li>
                                            """.trimMargin()
                                        }
                                    }

                                    unsafe {
                                        +"""
                                            <p>
                                            En caso de alguna reclamaci??n, el <b>ACREDITADO</b> deber?? presentarla por escrito, ya sea f??sica o electr??nicamente, a la Unidad Especializada de Atenci??n a Usuarios.
                                            <br> <br>
                                            La Unidad Especializada de Atenci??n a Usuarios emitir?? una respuesta y se la notificar?? al <b>ACREDITADO</b> en un plazo que no exceder?? de 30 (treinta) d??as naturales contados a partir de la presentaci??n de la aclaraci??n, inconformidad, reclamaci??n o queja.
                                            <br> <br>
                                            <b>TRIG??SIMA SEGUNDA. LEGISLACI??N APLICABLE Y JURISDICCI??N</b>. Para todo lo relativo a la interpretaci??n y cumplimiento del presente Contrato, las Partes convienen en sujetarse a lo establecido en las diversas disposiciones emitidas por las autoridades financieras competentes, a las contenidas en las leyes mercantiles y civiles que sean aplicables, as?? como a las sanas pr??cticas y usos bancarios y mercantiles de la Rep??blica Mexicana.
                                            <br> <br>
                                            Asimismo, las Partes convienen expresamente en que la soluci??n de controversias que llegaren a surgir se someter??n a los tribunales competentes de la Ciudad de M??xico, renunciando expresamente a cualquier otro fuero que pudiere corresponderles en virtud de sus domicilios presentes o futuros o por cualquier otra raz??n. No obstante lo anterior, en caso de cualquier controversia derivada del presente Contrato, el ACREDITADO tendr?? la facultad, s?? as?? lo decide, de acudir previamente a la Comisi??n Nacional para la Protecci??n y Defensa de los Usuarios de Servicios Financieros.
                                            <br> <br>
                                            El n??mero telef??nico de atenci??n a usuarios de la <b>CONDUSEF</b> es: (5340-0999 o LADA sin costo 01-800-999-8080), direcci??n en Internet (www.condusef.gob.mx) y correo electr??nico (asesoria@condusef.gob.mx).
                                            <br> <br>
                                            Es del conocimiento del <b>ACREDITADO</b> que la <b>SOFOM</b> no requiere autorizaci??n de la Secretar??a de Hacienda y Cr??dito P??blico; y para la realizaci??n de sus operaciones est?? sujeta a la supervisi??n de la Comisi??n Nacional Bancaria y de Valores ??nicamente para efectos de lo dispuesto por el art??culo 56 de la Ley General de Organizaciones y Actividades Auxiliares del Cr??dito.
                                            <br> <br>
                                            El presente Contrato, datos y referencias consignados en la car??tula y <b>SOLICITUD</b>, constituyen la expresi??n de la voluntad de las Partes, por lo que se suscribe por la <b>SOFOM</b> y por el <b>ACREDITADO</b> en la Ciudad de <b>[entidad federativa donde se suscribe el documento]</b> a los <b>[indicar d??a]</b> d??as del mes de <b>[indicar mes]</b>  del a??o <b>[indicar a??o]</b>.
                                            <br> <br>
                                            EN ESTE ACTO SE LE ENTREGA UNA COPIA DEL PRESENTE CONTRATO AL <b>ACREDITADO</b> EN FORMA F??SICA O POR MEDIOS ELECTR??NICOS.
                                            </p>
                                            """.trimMargin()
                                    }

                                    div("row") {
                                        style = "margin-top:50px;"
                                        div("col-md-6") {
                                            style = "padding:0px 30px;"
                                            h6 {
                                                style = "text-align:center; font-weight:bold; margin-bottom:64px;"
                                                +"SOFOM"
                                            }
                                            hr {
                                                style = "height:2px; color:#000; margin-top:30px;"
                                            }
                                            p {
                                                style = "padding:0; font-size:14px; text-align:center;"
                                                +"Karum Operadora de Pagos S.A.P.I. de C.V., SOFOM E.N.R."
                                            }
                                        }
                                        div("col-md-6") {
                                            style = "padding:0px 30px;"
                                            h6 {
                                                style = "text-align:center; font-weight:bold; margin-bottom:64px"
                                                +"ACREDITADO"
                                            }
                                            h6 {
                                                style = "text-align:center; font-weight:bold; margin-top:25px;"
//                                                +"[Firma del ACREDITADO]"
                                            }
                                            hr {
                                                style = "height:2px; color:#000; margin-top:30px;"
                                            }
                                            p {
                                                style =
                                                    "font-weight:bold; text-align:center; font-size:14px; padding:0;"
//                                                +"[Nombre completo del ACREDITADO]"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    div {
                        style = "text-align:center; margin:30px 0px; cursor: pointer;"
                        a(classes = "declaration-btn") {
//                            href = "/goodBye".withBaseUrl()
                            onClick = "onNotAgreeBtnClick()"
                            +"No estoy de acuerdo"
                        }

                        a(classes = "declaration-btn") {
                            id = "declarationAcceptBtn"
                            style = "cursor: pointer;"
                            onClick = "onAcceptBtnClick()"
                            +"De acuerdo"
                        }
                    }

                    form {
                        div {
                            div(classes = "toast") {
                                style = "margin:auto; background:none; border:none; box-shadow: none;"
                                div(classes = "toast-body") {
                                    h4("mr-auto text-danger") {
                                        style = "text-align:center;"
                                        id = "add_toast_id"
                                    }
                                }
                            }
                        }

                    }
                }

                div(classes = "modal fade") {
                    style = "padding-top:70px;"
                    id = "completeModal"
                    role = "dialog"

                    div(classes = "modal-dialog") {
                        style = "width:75%; margin:auto;"
                        div("modal-content") {
                            style = "background-color:#fff; border:4px solid #ffdcB3; border-radius:10px;"
                            div("modal-body") {
                                h3 {
                                    style = "font-weight:700; color:#ff6700; text-align:center;"
                                    +"Tr??mite enviado!"
                                }
                                div {
                                    style = "width:96%; margin:auto; margin-top:20px;"
                                    h6 {
                                        style = "padding-left: 32px; color: #000; font-weight: 500;"
                                        +"Siguientes pasos:"
                                    }

                                    /*ul {
                                        style = "color:#000; line-height:1.8rem;"
                                        li { +"Nuesta Central de Cr??dito analizar?? tu tr??mite." }
                                        li { +"En caso de requerir alg??n documento o informaci??n adicional te llamar??." }
                                        li { +"Una vez autorizado tu cr??dito, recibiras un mensaje de texto con tu n??mero de referencia para realizar el pago del 30% en tiendas OXXO." }
                                    }*/

                                    ul {
                                        style = "color:#000; font-size:0.85rem;"
                                        li { +"Nuestra Central de Cr??dito proceder?? a evaluar su solicitud de cr??dito." }
                                        li { +"En caso de llegar a requerir alg??n documento o informaci??n adicional uno de nuestros ejecutivos se comunicar?? con usted." }
                                        li { +"Una vez que su solicitud de cr??dito haya sido evaluada, recibir?? un SMS para informarle el estatus final de su solicitud." }
                                        li { +"De ser autorizado su cr??dito, recibir?? un mensaje de texto (SMS), el cual contendr?? su n??mero de referencia ??nico para realizar el pago del enganche en tiendas OXXO, correspondiente al 30% del costo de su nuevo tel??fono celular. El pago deber?? ser efectuado en una sola exhibici??n, por la cantidad exacta que se indica en el mensaje." }
                                        li { +"Su n??mero de referencia ??nico tendr?? una vigencia de 7 d??as naturales. De no realizar el pago en este plazo, la venta ser?? cancelada." }
                                        li { +"Una vez efectuado el pago del enganche, comenzar?? el proceso de preparaci??n para el env??o de su tel??fono a su domicilio." }
                                        li { +"Recibir?? un mensaje SMS con su n??mero de referencia permanente, con el cual podr?? efectuar el pago de sus mensualidades solamente en tiendas OXXO." }
                                    }
                                }

                                div {
                                    style = "width:15%; margin:10px auto;"
                                    img {
                                        style = "text-align:center;"
                                        src = "/assets/media/tick.png"
                                        width = "65px"
                                        height = "65px"
                                    }
                                }

                                /*h3 {
                                    style = "color:#ffb700; text-align:center; margin-top:10px; font-size:1.2rem;"
                                    +"N??mero de Confirmaci??n"
                                }*/

                                /*h4 {
                                     style =
                                         "color:#fff; text-align:center; margin-top:10px; font-size:17px;padding:0px 25px;"
                                     +"Se envie el numero de cuenta"
                                 }*/

                                /*h3 {
                                    id = "tc44Code"
                                    style = "color:#000; margin-top:10px; text-align:center; font-weight:700;"
                                    +"XXXXXX"
                                }*/

                                div {
                                    style = "text-align:center;"
                                    a(classes = "completeBtn") {
                                        href = "/goodBye".withBaseUrl()
                                        +"Entendido"
                                    }

                                    /*h4 {
                                        style = "color:#000; text-align:center; margin-top:10px; font-size:1rem;"
                                        +"Numero de confirmacion"
                                    }*/
                                }
                            }
                        }
                    }
                }

                div(classes = "modal fade") {
                    style = "padding-top:100px;"
                    id = "notAgreeModal"
                    role = "dialog"

                    div(classes = "modal-dialog") {
                        style = "width:75%; margin:auto;"
                        div("modal-content") {
                            style = "background-color:#fff; border:4px solid #ffdcB3; border-radius:10px;"
                            div("modal-body") {
                                style = "padding:50px 0px;"
                                h3 {
                                    style = "font-weight:700; color:#ff6700; text-align:center;"
                                    +"En desacuerdo!"
                                }
                                div {
                                    style = "width:90%; margin:auto; margin-top:25px; padding:15px;"
                                    h6 {
                                        id = "firstBtnClickText"
                                        style = "color:#000; text-align:center; font-size:1.1rem; display:none;"
                                        +"""Para continuar y aprobar su cr??dito, as?? como la compra del equipo celular 
                                            necesitas estar de acuerdo con este contrato.""".trimIndent()
                                    }

                                    h6 {
                                        id = "secondBtnClickText"
                                        style = "color:#000; text-align:center; font-size:1.1rem; display:none;"
                                        +"""Tu solicitud no se procesar?? debido a que no aceptaste el contrato de cr??dito.
                                            Muchas gracias.
                                        """.trimIndent()
                                    }
                                }

                                div {
                                    style = "text-align:center;"
                                    a(classes = "completeBtn") {
                                        style = "cursor:pointer;"
                                        onClick = "onCloseBtnClick()"
                                        +"OK"
                                    }
                                }
                            }
                        }
                    }
                }

                modalLoader()
                jscript(src = "assets/js/main.js")
                jscript(src = "assets/js/declaration-page.js")
            }
        }
    }

    suspend fun goodByePage() {
        /*   val userSession = call.userSession
           val user = UserModel.getUser(userSession.mobile) ?: error("Invalid session!")
           if (user.status < UserModel.MOBILE_DATA_COMPLETE) {
               if (pageRedirect()) return
           }*/
        call.respondHtml(HttpStatusCode.OK) {
            head {
                meta(charset = "utf-8")
                title("Karum Application")
                meta(name = "description", content = "Approva System")
                meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                //<!--begin::Fonts -->
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                )

//                css("assets/css/info-style.css")
                css("assets/css/goodBye-style.css")
                css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

            }

            body {
//                div("goodBye")
                div(classes = "goodBye-instant") {
                    img {
                        src = "assets/media/instant-logo.png"
                    }
                    h1 { +"Tu smartphone al instante." }
                }
            }
        }
    }

    /*

            data class Stat(val title: String, val subtitle: String, val value: String, val colSize: String)

        private fun FlowContent.dashBoardStatRow2(stats: List<Stat>) {
            val fonts = listOf("kt-font-brand", "kt-font-success", "kt-font-warning", "kt-font-danger")
            //<!--begin:: Widgets/Stats-->
            div(classes = "kt-portlet") {
                div(classes = "kt-portlet__body  kt-portlet__body--fit") {
                    div(classes = "row row-no-padding row-col-separator-lg") {
                        stats.forEachIndexed { index, stat ->
                            div(classes = "col-md-12 col-lg-6 col-xl-${stat.colSize}") {

                                //<!--begin::Total Profit-->
                                div(classes = "kt-widget24") {
                                    div(classes = "kt-widget24__details") {
                                        div(classes = "kt-widget24__info") {
                                            h4(classes = "kt-widget24__title") {
                                                +stat.title
                                            }
                                            span(classes = "kt-widget24__desc") {
                                                +stat.subtitle
                                            }
                                        }
                                        span(classes = "kt-widget24__stats ${fonts[index % fonts.size]}") {
                                            id = "first-stat-value"
                                            +stat.value
                                        }
                                    }
                                    /*div(classes = "progress progress--sm") {
                                        div(classes = "progress-bar kt-bg-brand") {
                                            role = "progressbar"
                                            style = "width: 78%;"
            //        aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"
                                        }
                                    }
                                    div(classes = "kt-widget24__action") {
                                        span(classes = "kt-widget24__change") {
                                            +"Change"
                                        }
                                        span(classes = "kt-widget24__number") {
                                            +"78%"
                                        }
                                    }*/
                                }

                                //<!--end::Total Profit-->
                            }
                        }
                    }
                }
            }

            //<!--end:: Widgets/Stats-->
        }


           suspend fun indexPage() {
            pageTemplate {
                css("assets/css/dashboard.css")

                h3 {
                    +"Karum Card Management!"
                }
                val registered = 100
                val approved = 35
                val pending = 75

                dashBoardStatRow2(
                    listOfNotNull(
                        Stat("Registered", "Application", "$registered", "3"),
                        Stat("Approved", "Application", "$approved", "3"),
                        Stat("Pending", "Application", "$pending", "3"),
                    )
                )
            }
        }

        suspend fun questionnairePage() {
            pageTemplate {
                portlet(title = "Request For Karum Card") {
                    div {
                        style = "display:block; margin-left:auto; margin-right:auto; margin-bottom:32px;"
                        h4 {
                            style = "color:black; margin-bottom:24px;"
                            +"We want to know you better, so please answer the following questions carefully!"
                        }

                        div(classes = "row") {
                            style = "padding-left:32px;"
                            div(classes = "col-lg-6") {
                                ul {
                                    style = "list-style-type:circle; font-size:18px; padding:0;"
                                    li {
                                        +"I declare under pretense of telling the truth that I do not currently hold or during the immediately preceding year any prominent public position at the federal, state, municipal or district level in Mexico or abroad"
                                    }
                                }
                            }
                            div(classes = "col-lg-2") {
                                style = "margin-top:auto; margin-bottom:auto; margin-top:auto; margin-bottom:auto;"
                                div {
                                    div(classes = "form-check") {
                                        style = "margin-bottom:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "yesIAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"Yes, I Accept" }
                                    }
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "iDoNotAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"I do not Accept" }
                                    }
                                }
                            }
                        }

                        div(classes = "row") {
                            style = "margin-top:32px; padding-left:32px;"
                            div(classes = "col-lg-6") {
                                ul {
                                    style = " list-style-type:circle; font-size:18px; padding:0;"
                                    li {
                                        +"I declare under pretense of telling the truth that I do not currently hold or during the immediately preceding year any prominent public position at the federal, state, municipal or district level in Mexico or abroad"
                                    }
                                }
                            }
                            div(classes = "col-lg-2") {
                                style = "margin-top:auto; margin-bottom:auto; margin-top:auto; margin-bottom:auto;"

                                div {
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "yesIAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"Yes, I Accept" }
                                    }
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "iDoNotAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"I do not Accept" }
                                    }
                                }
                            }
                        }

                        div(classes = "row") {
                            style = "margin-top:32px; padding-left:32px;"
                            div(classes = "col-lg-6") {
                                ul {
                                    style = " list-style-type:circle; font-size:18px; padding:0;"
                                    li {
                                        +"I also declare that my spouse, if any, or relative by consanguinity of affinity up to the 2nd degree, does no currently hold or during the immediately preceding year any outstanding public office at the federal state, municipal or district level in Mexico or abroad"
                                    }
                                }
                            }
                            div(classes = "col-lg-2") {
                                style = "margin-top:auto; margin-bottom:auto; margin-top:auto; margin-bottom:auto;"

                                div {
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "yesIAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"Yes, I Accept" }
                                    }
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "iDoNotAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"I do not Accept" }
                                    }
                                }
                            }
                        }

                        div(classes = "row") {
                            style = "margin-top:32px; padding-left:32px;"
                            div(classes = "col-lg-6") {
                                ul {
                                    style = " list-style-type:circle; font-size:18px; padding:0;"
                                    li {
                                        +"I also declare that my spouse, if any, or relative by consanguinity of affinity up to the 2nd degree, does no currently hold or during the immediately preceding year any outstanding public office at the federal state, municipal or district level in Mexico or abroad"
                                    }
                                }
                            }
                            div(classes = "col-lg-2") {
                                style = "margin-top:auto; margin-bottom:auto; margin-top:auto; margin-bottom:auto;"

                                div {
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "yesIAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"Yes, I Accept" }
                                    }
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "iDoNotAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"I do not Accept" }
                                    }
                                }
                            }
                        }

                        div(classes = "row") {
                            style = "margin-top:32px; padding-left:32px;"
                            div(classes = "col-lg-6") {
                                ul {
                                    style = " list-style-type:circle; font-size:18px; padding:0;"
                                    li {
                                        +"I also declare that my spouse, if any, or relative by consanguinity of affinity up to the 2nd degree, does no currently hold or during the immediately preceding year any outstanding public office at the federal state, municipal or district level in Mexico or abroad"
                                    }
                                }
                            }
                            div(classes = "col-lg-2") {
                                style = "margin-top:auto; margin-bottom:auto; margin-top:auto; margin-bottom:auto;"

                                div {
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "yesIAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"Yes, I Accept" }
                                    }
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "iDoNotAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"I do not Accept" }
                                    }
                                }
                            }
                        }

                        div(classes = "row") {
                            style = "margin-top:32px; padding-left:32px;"
                            div(classes = "col-lg-6") {
                                ul {
                                    style = " list-style-type:circle; font-size:18px; padding:0;"
                                    li {
                                        +"I also declare that my spouse, if any, or relative by consanguinity of affinity up to the 2nd degree, does no currently hold or during the immediately preceding year any outstanding public office at the federal state, municipal or district level in Mexico or abroad"
                                    }
                                }
                            }
                            div(classes = "col-lg-2") {
                                style = "margin-top:auto; margin-bottom:auto; margin-top:auto; margin-bottom:auto;"

                                div {
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "yesIAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"Yes, I Accept" }
                                    }
                                    div(classes = "form-check") {
                                        style = "margin-top:8px;"
                                        input(classes = "form-check-input", type = InputType.checkBox) {
                                            value = ""
                                            id = "iDoNotAcceptId"
                                        }
                                        label(classes = "form-check-label") { +"I do not Accept" }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        suspend fun cardApplicationPage() {

            pageTemplate {

                portlet(title = "Request For Karum Card") {
                    form {
                        id = "application-form"
                        method = FormMethod.post

                        div(classes = "row") {
                            style = "margin-bottom:32px;"
                            div("col-lg-3") {
                                formInputWithLabel(label = "Store Number", type = InputType.number) {
                                    min = "0"
                                    name = "store_number"
                                }
                            }

                            div("col-lg-3") {
                                formInputWithLabel(label = "Promoter Key", type = InputType.text) {
                                    name = "promoter_key"
                                }
                            }

                            div("col-lg-3") {
                                formInputWithLabel(label = "Type of Request", type = InputType.text) {
                                    name = "type_of_request"
                                }
                            }
                        }

                        div("row") {
                            style = "border-style: dotted; padding:32px; "

                            div("col-lg-12") {
                                style = "margin-bottom:24px;"
                                h4 { +"Personal Information" }
                            }


                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "First Name", type = InputType.text) {
                                    name = "first_name"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Last Name", type = InputType.text) {
                                    name = "first_name"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Mobile", type = InputType.text) {
                                    name = "mobile_number"
                                }
                            }

                            div(classes = "col-lg-4") {
                                style = "margin-bottom:16px;"
                                label { +"Gender" }
                                select(classes = "form-control") {
                                    name = "state_of_birth"
                                    option {
                                        value = ""
                                        +"Select"
                                    }
                                    option {
                                        value = "1"
                                        +"Men"
                                    }

                                    option {
                                        value = "2"
                                        +"Women"
                                    }


                                }
                            }

                            div(classes = "col-lg-4") {
                                style = "margin-bottom:16px;"
                                label { +"Fecha de Nacimento" }
                                dateInput(classes = "form-control") {
                                    name = "date_of_birth"
                                    placeholder = "Fecha de Nacimento"
                                    value = LocalDate.now().toString()
                                }
                            }

                            div(classes = "col-lg-4") {
                                style = "margin-bottom:16px;"
                                label { +"State of birth" }
                                select(classes = "form-control") {
                                    name = "state_of_birth"
                                    option {
                                        value = "1"
                                        +"California"
                                    }
                                    option {
                                        value = "2"
                                        +"Alaska"
                                    }

                                    option {
                                        value = "3"
                                        +"Texas"
                                    }

                                    option {
                                        value = "4"
                                        +"Arizona"
                                    }

                                    option {
                                        value = "5"
                                        +"Vermont"
                                    }
                                }
                            }

                            div(classes = "col-lg-4") {
                                button(classes = "btn btn-warning") {
                                    id = "generate_curp"
                                    style = "margin-top:15px;"
                                    +"Generate CURP"
                                }
                            }

                        }

                        div("row") {
                            style = "border-style: dotted; padding:32px; margin-top:32px"

                            div("col-lg-12") {
                                style = "margin-bottom:24px;"
                                h4 { +"Home Information" }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Street Number", type = InputType.text) {
                                    name = "street_number"
                                }
                            }

                            div("col-lg-2") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Ext No.", type = InputType.number) {
                                    name = "ext_no"
                                }
                            }

                            div("col-lg-2") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Int No.", type = InputType.number) {
                                    name = "int_no"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "C.P", type = InputType.number) {
                                    name = "cp_number"
                                }
                            }

                            div(classes = "col-lg-4") {
                                style = "margin-bottom:16px;"
                                label { +"State" }
                                select(classes = "form-control") {
                                    disabled = true
                                    name = "state"
                                    option {
                                        value = ""

                                    }
                                    option {
                                        value = "1"
                                        +"Mexico"
                                    }

                                    option {
                                        value = "2"
                                        +"Arizona"
                                    }


                                }
                            }

                            div(classes = "col-lg-4") {
                                style = "margin-bottom:16px;"
                                label { +"Municipality" }
                                select(classes = "form-control") {
                                    disabled = true
                                    name = "state"
                                    option {
                                        value = ""

                                    }
                                    option {
                                        value = "1"
                                        +"Mexico"
                                    }

                                    option {
                                        value = "2"
                                        +"Arizona"
                                    }


                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Town", type = InputType.text) {
                                    name = "Town"
                                }
                            }


                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Cell Phone", type = InputType.text) {
                                    name = "cell_phone"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Confirm Cell Phone", type = InputType.text) {
                                    name = "confirm_cell_phone"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Email", type = InputType.text) {
                                    name = "email"
                                }
                            }

                        }

                        div("row") {
                            style = "border-style: dotted; padding:32px; margin-top:32px; "

                            div("col-lg-12") {
                                style = "margin-bottom:24px;"
                                h4 { +"Financial Information" }
                            }


                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Company Name", type = InputType.text) {
                                    name = "company_name"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Company Phone", type = InputType.text) {
                                    name = "company_phone"
                                    maxLength = "10"
                                }
                            }

                            div("col-lg-4") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Monthly Income", type = InputType.number) {
                                    name = "monthly_income"
                                }
                            }

                        }

                        div("row") {
                            style = "border-style: dotted; padding:32px; margin-top:32px; "

                            div("col-lg-12") {
                                style = "margin-bottom:24px;"
                                h4 { +"National Identification" }
                            }

                            div(classes = "col-lg-5") {
                                style = "margin-bottom:16px;"
                                label { +"Form of Identification" }
                                select(classes = "form-control") {
                                    name = "state_of_birth"
                                    option {
                                        value = "1"
                                        +"IFE/INE"
                                    }
                                }
                            }

                            div("col-lg-5") {
                                style = "margin-bottom:16px;"
                                formInputWithLabel(label = "Passport number", type = InputType.text) {
                                    name = "passport_number"
                                }
                            }

                            div(classes = "col-lg-5") {
                                label { +"Front Identification" }
                                fileInput(classes = "form-control") { name = "ine_front_side" }
                            }

                            div(classes = "col-lg-5") {
                                label { +"Back Identification" }
                                fileInput(classes = "form-control") { name = "ine_back_side" }
                            }

                        }

                        div(classes = "form-check") {
                            style = "margin-top:24px"
                            input(classes = "form-check-input", type = InputType.checkBox) {
                                value = ""
                                id = ""
                            }
                            label(classes = "form-check-label") { +"I Accept" }

                        }

                        div(classes = "form-check") {
                            style = "margin-top:16px;"
                            input(classes = "form-check-input", type = InputType.checkBox) {
                                value = ""
                                id = ""
                            }
                            label(classes = "form-check-label") { +"I Accept" }

                        }

                    }

                    modal(
                        "modal_otp_id",
                        "Authorization For",
                        "Send",
                        primaryButtonId = "modal_otp_id",
                        modalSize = ModalSize.LARGE,
                    ) {
                        form {
                            method = FormMethod.post
                            p {
                                style = "font-size:16px;"
                                +"Through this channel, I expressly authorize Karum Operadora de Pagos SAPI de CV to investigate my credit behavior in the credit information companies that it deems appropriate in accordance with the provisions of Art."
                            }

                            div {
                                style =
                                    "display:block; margin-left:auto; margin-right:auto; margin-top:32px; margin-bottom:32px;"

                                label { +"To Authorize enter OTP:" }
                                input(classes = "form-") { }
                                div(classes = "col-lg-4") {
                                    formInputWithLabel(label = "To Authorize enter OTP:", type = InputType.text) {
                                        name = "otp_num"
                                    }
                                }
                            }
                        }
                    }

                    div(classes = "row") {

                        button(classes = "btn btn-brand btn-primary", type = ButtonType.button) {
                            attributes.apply {
                                put("data-toggle", "modal")
                                put("data-target", "#modal_otp_id")
                            }
                            id = "settings-form-button"
                            style = "margin-top:15px; margin-top:24px;"
                            +"Send Application"
                        }
                    }

                }

                bottomBlock = {
                    jscript("assets/js/card_application.js")
                }
            }
        }

        suspend fun supplementaryDataPage() {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    meta(charset = "utf-8")
                    title("Karum Application")
                    meta(name = "description", content = "Approva System")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                    //<!--begin::Fonts -->
                    link(
                        rel = "stylesheet",
                        href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                    )

                    css("assets/css/info-style.css")
                    css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                    css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                    jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                    jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                    jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                    jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                    jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

                }

                body {
                    section {
                        div("main-container") {
                            header(classes = "info-header") {
                                span("karum-logo2") {
                                    img {
                                        src = "/assets/media/karum-logo.png"
                                        height = "80px"
                                    }
                                }
                                h1(classes = "splash-title-left") {
                                    style = "display:inline-block;"
                                    +"APPROVA"
                                }
                                h2(classes = "splash-subtitle-left") {
                                    style = "display:inline-block;"
                                    +"- Pre - calificaci??n"
                                }
                            }

                            div(classes = "container") {
                                form {
                                    div("row") {
                                        style = "padding:20px 25px;"

                                        h3("form-heading") { +"Datos Complementarios" }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label { +"Pa??s de nacimiento" }
                                                p {
                                                    style = "color:#fff; font-size:1rem;"
                                                    +"MEXICO"
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label { +"Nacionalidad" }
                                                p {
                                                    style = "color:#fff; font-size:1rem;"
                                                    +"MEXICANA"
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Medio de env??o del estado de cuenta" }
                                                p {
                                                    style = "color:#fff; font-size:1rem;"
                                                    +"ELECTRONICO"
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Profesion" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Telefono de Casa" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Antiguedad Domicilio" }
                                                br
                                                label { +"A??os:" }
                                                select(classes = "info-form-select-short") {
                                                    style = "width:25%;"
                                                    name = "year"
                                                    id = "year"
                                                }

                                                label {
                                                    style = "margin-left: 10px;"
                                                    +"Meses:"
                                                }
                                                select(classes = "info-form-select-short") {
                                                    style = "width:30%;"
                                                    name = "month"
                                                    id = "month"
                                                }
                                                select(classes = "info-form-select-short") {
                                                    id = "day"
                                                    name = "day"
                                                    style = "display:none;"
                                                }
                                            }
                                        }

                                    }
                                }

                                div {
                                    style = "text-align:left; display:inline-block;"
                                    a {
                                        style = "width:45px; height:45px; display:none;"
                                        onClick = "window.history.go(-1); return false;"

                                        img {
                                            src = "/assets/media/back.png"
                                            width = "60px"
                                            height = "60px"
                                        }
                                    }
                                }

                                div {
                                    style = "float:right;display:inline-block; margin-top:-48px;"
                                    a {
                                        style = "width:45px; height:45px;"
                                        href = "/economicData".withBaseUrl()
                                        img {
                                            src = "/assets/media/forward.png"
                                            width = "60px"
                                            height = "60px"
                                        }
                                    }
                                }

                            }

                        }
                    }
                    jscript(src = "assets/js/info-page.js")
                }
            }
        }

        suspend fun economicDataPage() {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    meta(charset = "utf-8")
                    title("Karum Application")
                    meta(name = "description", content = "Approva System")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")

                    //<!--begin::Fonts -->
                    link(
                        rel = "stylesheet",
                        href = "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700|Asap+Condensed:500"
                    )

                    css("assets/css/info-style.css")
                    css(href = "https://use.fontawesome.com/releases/v5.15.4/css/all.css")
                    css("https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                    jscript(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")

                    jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js")
                    jscript(src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js")
                    jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js")
                    jscript(src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js")

                }

                body {
                    section {
                        div("main-container") {
                            header(classes = "info-header") {
                                span("karum-logo2") {
                                    img {
                                        src = "/assets/media/karum-logo.png"
                                        height = "80px"
                                    }
                                }
                                h1(classes = "splash-title-left") {
                                    style = "display:inline-block;"
                                    +"APPROVA"
                                }
                                h2(classes = "splash-subtitle-left") {
                                    style = "display:inline-block;"
                                    +"- Pre - calificaci??n"
                                }
                            }

                            div(classes = "container") {
                                style = "position:relative;"
                                span {
                                    style = "position:absolute; right:10px; top:10px;"
                                    img {
                                        src = "/assets/media/qr-code.gif"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }
                                form {
                                    div("row") {
                                        style = "padding:20px 25px;"

                                        h3("form-heading") { +"Datos laborales y econ??micos del solicitante " }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-12") {
                                                label(classes = "form-custom") { +"Nombre de la empresa" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:100%;"
                                                    placeholder = " "
                                                    maxLength = "12"
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-12") {
                                                input(classes = "custom-info-form-0", type = InputType.checkBox) {
                                                    style = " display:inline-block;"
                                                }
                                                p {
                                                    style = "color:#ffb700; font-size:0.875rem; display:inline-block;"
                                                    +"Tu direccionade trabajo es la misma que la tu domicillio"
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Calle" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-2") {
                                                label(classes = "form-custom") { +"No Ext" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-2") {
                                                label(classes = "form-custom") { +"No Int" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"C.P" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:100%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Estado" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Alcald??a / Municipio" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Colonia" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Ciudad" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Antiguedad" }
                                                br
                                                label { +"A??os:" }
                                                select(classes = "info-form-select-short") {
                                                    style = "width:25%"
                                                    name = "year"
                                                    id = "year"
                                                }

                                                label { +"Meses:" }
                                                select(classes = "info-form-select-short") {
                                                    style = "width:30%"
                                                    name = "month"
                                                    id = "month"
                                                }
                                                select {
                                                    style = "display:none"
                                                    name = "day"
                                                    id = "day"
                                                }
                                            }
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Giro" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:100%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }
                                        }

                                        div(classes = "row") {
                                            style = "margin-bottom:15px; font-size:1rem;"
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Ocupaci??n" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }

                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Ingreso Mensual Bruto" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:85%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }
                                            div(classes = "col-md-4") {
                                                label(classes = "form-custom") { +"Telefono de la Empresa" }
                                                input(classes = "custom-info-form", type = InputType.text) {
                                                    style = "width:100%;"
                                                    required = true
                                                    placeholder = " "
                                                }
                                            }
                                        }

                                        *//*div(classes = "row") {
                                        style = "margin-bottom:0px; font-size:16px;"
                                        div(classes = "col-md-12") {
                                            input(classes = "custom-info-form-0", type = InputType.checkBox) {
                                                style = " display:inline-block;"
                                            }
                                            p {
                                                style = "color:#ffb700; font-size:14px; display:inline-block;"
                                                +"La direccion de entrega del telefono movil es la misma que la del domicilio"
                                            }
                                        }
                                    }

                                    div(classes = "row") {
                                        style = "margin-bottom:15px; font-size:16px;"
                                        div(classes = "col-md-12") {
                                            input(classes = "custom-info-form-0", type = InputType.checkBox) {
                                                style = " display:inline-block;"
                                            }
                                            p {
                                                style = "color:#ffb700; font-size:14px; display:inline-block;"
                                                +"La direccion de entrega del telefono movil es la misma que la del trabajo"
                                            }
                                        }
                                    }*//*

                                }
                            }

                            div {
                                style = "text-align:left; display:inline-block;"
                                a {
                                    style = "width:45px; height:45px;"
                                    onClick = "window.history.go(-1); return false;"

                                    img {
                                        src = "/assets/media/back.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }
                            }

                            div {
                                style = "float:right; display:inline-block;"
                                a {
                                    style = "width:45px; height:45px;"
                                    href = "/mobileData".withBaseUrl()
                                    img {
                                        src = "/assets/media/forward.png"
                                        width = "60px"
                                        height = "60px"
                                    }
                                }
                            }

                        }

                    }
                }

                jscript(src = "assets/js/info-page.js")
            }
        }
    }*/

}