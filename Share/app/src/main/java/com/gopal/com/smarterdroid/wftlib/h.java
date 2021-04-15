package com.smarterdroid.wftlib;

import java.util.Properties;

public final class h {
    private String a;
    private boolean b;
    private Properties c;

    public h(boolean z, String str, Properties properties) {
        this.a = str;
        this.b = z;
        this.c = properties;
    }

    public final String a() {
        StringBuilder sb = new StringBuilder(10000);
        sb.append("<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>" + v.d() + "</title>\r\n<style type=\"text/css\">\r\nbody {background-color: #88BCF6;\tfont-family: \"Verdana\";}\r\n#box { background: #EEEEEE; width: 300px; padding: 25px; border: 2px solid gray; margin-left: auto; margin-right: auto; margin-top: 200px; text-align: center}\r\n</style>\r\n");
        if (this.b) {
            String str = "http://";
            if (this.c.getProperty("sslmode", "false").equalsIgnoreCase("true")) {
                str = "https://";
            }
            sb.append("<script type=\"text/javascript\">\r\n<!--\r\nwindow.location = \"" + str + this.a + "\"\r\n//-->\r\n</script>\r\n");
        }
        sb.append("</head>\r\n<body onload=\"document.getElementById('passwd').focus();\">\r\n<div id=\"box\"><form action=\"/login.wft\" method=\"post\" enctype=\"multipart/form-data\" name=\"authdform\" id=\"authform\">\r\n" + v.d() + "<br><br>" + aj.h().getString(z.enterpassword) + "<br>\r\n<input type=\"password\" name=\"passwd\" id=\"passwd\"  size=\"21\" maxlength=\"20\" autofocus><br>\r\n");
        if (!this.b) {
            sb.append("<input type=\"hidden\" name=\"redirurl\" value=\"" + this.a + "\">\r\n");
        }
        sb.append("<input type=\"submit\" value=\"" + aj.h().getString(z.submit) + "\"> <input type=\"reset\" value=\"" + aj.h().getString(z.reset) + "\">\r\n<br><br><a href=\"http://www.smarterdroid.com/index.php/wifi-file-transfer/troubleshooting#forgotpassword\" style=\"font-size: 0.75em;\">" + aj.h().getString(z.forgotpassword) + "</a><br>\r\n</div></form>\r\n</body></html>");
        return sb.toString();
    }
}
