package com.api.ICPAEcommerce.services;

public class RecoverPasswordEmailMessage {

    public static String emailBody(String name, String link) {

        return "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\"/>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "    <title>E-mail HTML responsivo com botão</title>\n" +
                "</head>\n" +
                "<body class=\"\">\n" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                "    <tr>\n" +
                "        <td>&nbsp;</td>\n" +
                "        <td class=\"container\">\n" +
                "            <div class=\"header\">\n" +
                "                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                        <td class=\"align-center\" width=\"100%\">\n" +
                "                           <a href=\"https://site do ICPA-Ecommerce\"><img src=\"https://raw.githubusercontent.com/lucarauj/assets/0a17f80cf1e62a2b8e0cc70a840361d9fc15e842/brand.svg\" height=\"100\" alt=\"ICPA-Ecommerce\"></a>" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "            <div class=\"content\">\n" +
                "                <span class=\"preheader\"></span>\n" +
                "                <table role=\"presentation\" class=\"main\">\n" +
                "                    <tr>\n" +
                "                        <td class=\"wrapper\">\n" +
                "                            <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <p>&nbsp; Olá," + " " + name +"!" + "</p>\r\n" +
                "                                        <br><br>\n" +
                "                                        <p>&nbsp; Por favor, clique no link abaixo para redefinir sua password:</p>\n" +
                "                                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                               class=\"btn btn-primary\">\n" +
                "                                            <tbody>\n" +
                "                                            <tr>\n" +
                "                                                <td align=\"center\">\n" +
                "                                                    <table role=\"presentation\" border=\"0\" cellpadding=\"0\"\n" +
                "                                                           cellspacing=\"0\">\n" +
                "                                                        <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td><a href=\"https://www.icpaecommerce.com/"+link+"\\\" target=\"_blank\">Link de confirmação</a></td>\n" +
                "                                                            <br><br>\n" +
                "                                                        </tr>\n" +
                "                                                        </tbody>\n" +
                "                                                    </table>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                        <br><br>\n" +
                "                                        <p>&nbsp; Caso não tenha solicitado a alteração de password, por favor desconsidere\n" +
                "                                            este e-mail.</p>\n" +
                "                                        <br><br>\n" +
                "                                        <p>&nbsp; Atenciosamente,</p>\n" +
                "                                        <p>&nbsp; Equipe</p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "        <td>&nbsp;</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "<style>\n" +
                "\n" +
                "img {\n" +
                "    border: none;\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "    max-width: 100%; \n" +
                "}\n" +
                "  \n" +
                "body {\n" +
                "    background-color: #eaebed;\n" +
                "    font-family: sans-serif;\n" +
                "    -webkit-font-smoothing: antialiased;\n" +
                "    font-size: 14px;\n" +
                "    line-height: 1.4;\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "    -ms-text-size-adjust: 100%;\n" +
                "    -webkit-text-size-adjust: 100%; \n" +
                "}\n" +
                "  \n" +
                "table {\n" +
                "    border-collapse: separate;\n" +
                "    mso-table-lspace: 0pt;\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    min-width: 100%;\n" +
                "    width: 100%; \n" +
                "}\n" +
                "\n" +
                "table td {\n" +
                "      font-family: sans-serif;\n" +
                "      font-size: 14px;\n" +
                "      vertical-align: top; \n" +
                "}\n" +
                " \n" +
                "  \n" +
                ".body {\n" +
                "    background-color: #eaebed;\n" +
                "    width: 100%; \n" +
                "}\n" +
                "  \n" +
                ".container {\n" +
                "    display: block;\n" +
                "    Margin: 0 auto;\n" +
                "    max-width: 580px;\n" +
                "    padding: 10px;\n" +
                "    width: 580px; \n" +
                "}\n" +
                "\n" +
                ".content {\n" +
                "    box-sizing: border-box;\n" +
                "    display: block;\n" +
                "    Margin: 0 auto;\n" +
                "    max-width: 580px;\n" +
                "    padding: 10px; \n" +
                "}\n" +
                "  \n" +
                ".main {\n" +
                "    background: #ffffff;\n" +
                "    border-radius: 3px;\n" +
                "    width: 100%; \n" +
                "}\n" +
                "  \n" +
                ".header {\n" +
                "    padding: 20px 0;\n" +
                "}\n" +
                "  \n" +
                ".wrapper {\n" +
                "    box-sizing: border-box;\n" +
                "    padding: 20px; \n" +
                "}\n" +
                "  \n" +
                ".content-block {\n" +
                "    padding-bottom: 10px;\n" +
                "    padding-top: 10px;\n" +
                "}\n" +
                "  \n" +
                ".footer {\n" +
                "    clear: both;\n" +
                "    Margin-top: 10px;\n" +
                "    text-align: center;\n" +
                "    width: 100%; \n" +
                "}\n" +
                "\n" +
                ".footer td, .footer p, .footer span, .footer a {\n" +
                "      color: #9a9ea6;\n" +
                "      font-size: 12px;\n" +
                "      text-align: center; \n" +
                "}\n" +
                "\n" +
                "h1, h2, h3, h4 {\n" +
                "    color: #06090f;\n" +
                "    font-family: sans-serif;\n" +
                "    font-weight: 400;\n" +
                "    line-height: 1.4;\n" +
                "    margin: 0;\n" +
                "    margin-bottom: 30px; \n" +
                "}\n" +
                "  \n" +
                "h1 {\n" +
                "    font-size: 35px;\n" +
                "    font-weight: 300;\n" +
                "    text-align: center;\n" +
                "    text-transform: capitalize; \n" +
                "}\n" +
                "  \n" +
                "p, ul, ol {\n" +
                "    font-family: sans-serif;\n" +
                "    font-size: 14px;\n" +
                "    font-weight: normal;\n" +
                "    margin: 0;\n" +
                "    margin-bottom: 15px; \n" +
                "}\n" +
                "\n" +
                "p li, ul li, ol li {\n" +
                "      list-style-position: inside;\n" +
                "      margin-left: 5px; \n" +
                "}\n" +
                "  \n" +
                "a {\n" +
                "    color: #ec0867;\n" +
                "    text-decoration: underline; \n" +
                "}\n" +
                "  \n" +
                ".btn {\n" +
                "    box-sizing: border-box;\n" +
                "    width: 100%; \n" +
                "}\n" +
                "\n" +
                ".btn > tbody > tr > td {\n" +
                "      padding-bottom: 15px; \n" +
                "}\n" +
                "\n" +
                ".btn table {\n" +
                "      min-width: auto;\n" +
                "      width: auto; \n" +
                "}\n" +
                "\n" +
                ".btn table td {\n" +
                "      background-color: #ffffff;\n" +
                "      border-radius: 5px;\n" +
                "      text-align: center; \n" +
                "}\n" +
                "\n" +
                ".btn a {\n" +
                "      background-color: #ffffff;\n" +
                "      border: solid 1px #ec0867;\n" +
                "      border-radius: 5px;\n" +
                "      box-sizing: border-box;\n" +
                "      color: #ec0867;\n" +
                "      cursor: pointer;\n" +
                "      display: inline-block;\n" +
                "      font-size: 14px;\n" +
                "      font-weight: bold;\n" +
                "      margin: 0;\n" +
                "      padding: 12px 25px;\n" +
                "      text-decoration: none;\n" +
                "      text-transform: capitalize; \n" +
                "}\n" +
                "  \n" +
                ".btn-primary table td {\n" +
                "    background-color: #ec0867; \n" +
                "}\n" +
                "  \n" +
                ".btn-primary a {\n" +
                "    background-color: #ec0867;\n" +
                "    border-color: #ec0867;\n" +
                "    color: #ffffff; \n" +
                "}\n" +
                "  \n" +
                ".last {\n" +
                "    margin-bottom: 0; \n" +
                "}\n" +
                "  \n" +
                ".first {\n" +
                "    margin-top: 0; \n" +
                "}\n" +
                "  \n" +
                ".align-center {\n" +
                "    text-align: center; \n" +
                "}\n" +
                "  \n" +
                ".align-right {\n" +
                "    text-align: right; \n" +
                "}\n" +
                "  \n" +
                ".align-left {\n" +
                "    text-align: left; \n" +
                "}\n" +
                "  \n" +
                ".clear {\n" +
                "    clear: both; \n" +
                "}\n" +
                "  \n" +
                ".mt0 {\n" +
                "    margin-top: 0; \n" +
                "}\n" +
                "  \n" +
                ".mb0 {\n" +
                "    margin-bottom: 0; \n" +
                "}\n" +
                "  \n" +
                ".preheader {\n" +
                "    color: transparent;\n" +
                "    display: none;\n" +
                "    height: 0;\n" +
                "    max-height: 0;\n" +
                "    max-width: 0;\n" +
                "    opacity: 0;\n" +
                "    overflow: hidden;\n" +
                "    mso-hide: all;\n" +
                "    visibility: hidden;\n" +
                "    width: 0; \n" +
                "}\n" +
                "  \n" +
                ".powered-by a {\n" +
                "    text-decoration: none; \n" +
                "}\n" +
                "  \n" +
                "hr {\n" +
                "    border: 0;\n" +
                "    border-bottom: 1px solid #f6f6f6;\n" +
                "    Margin: 20px 0; \n" +
                "}\n" +
                "  \n" +
                "@media only screen and (max-width: 620px) {\n" +
                "    table[class=body] h1 {\n" +
                "      font-size: 28px;\n" +
                "      margin-bottom: 10px; \n" +
                "    }\n" +
                "    table[class=body] p,\n" +
                "    table[class=body] ul,\n" +
                "    table[class=body] ol,\n" +
                "    table[class=body] td,\n" +
                "    table[class=body] span,\n" +
                "    table[class=body] a {\n" +
                "      font-size: 16px; \n" +
                "    }\n" +
                "    table[class=body] .wrapper,\n" +
                "    table[class=body] .article {\n" +
                "      padding: 10px; \n" +
                "    }\n" +
                "    table[class=body] .content {\n" +
                "      padding: 0; \n" +
                "    }\n" +
                "    table[class=body] .container {\n" +
                "      padding: 0;\n" +
                "      width: 100%; \n" +
                "    }\n" +
                "    table[class=body] .main {\n" +
                "      border-left-width: 0;\n" +
                "      border-radius: 0;\n" +
                "      border-right-width: 0; \n" +
                "    }\n" +
                "    table[class=body] .btn table {\n" +
                "      width: 100%; \n" +
                "    }\n" +
                "    table[class=body] .btn a {\n" +
                "      width: 100%; \n" +
                "    }\n" +
                "    table[class=body] .img-responsive {\n" +
                "      height: auto;\n" +
                "      max-width: 100%;\n" +
                "      width: auto; \n" +
                "    }\n" +
                " }\n" +
                "  \n" +
                " @media all {\n" +
                "    .ExternalClass {\n" +
                "      width: 100%; \n" +
                "    }\n" +
                "    .ExternalClass,\n" +
                "    .ExternalClass p,\n" +
                "    .ExternalClass span,\n" +
                "    .ExternalClass font,\n" +
                "    .ExternalClass td,\n" +
                "    .ExternalClass div {\n" +
                "      line-height: 100%; \n" +
                "    }\n" +
                "    .apple-link a {\n" +
                "      color: inherit;\n" +
                "      font-family: inherit;\n" +
                "      font-size: inherit;\n" +
                "      font-weight: inherit;\n" +
                "      line-height: inherit;\n" +
                "      text-decoration: none; \n" +
                "    }\n" +
                "    .btn-primary table td:hover {\n" +
                "      background-color: #d5075d; \n" +
                "    }\n" +
                "    .btn-primary a:hover {\n" +
                "      background-color: #d5075d;\n" +
                "      border-color: #d5075d; \n" +
                "    } \n" +
                " }\n" +
                "  \n" +
                "</style>\n" +
                "</html>";
    }
}
