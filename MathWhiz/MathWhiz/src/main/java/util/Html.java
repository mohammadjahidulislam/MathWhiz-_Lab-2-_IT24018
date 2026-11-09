package util;

import jakarta.servlet.http.HttpServletRequest;

public final class Html {
    private Html() { }

    /** Escapes text so it is safe to print inside HTML. */
    public static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    /** Returns an escaped request parameter (empty string if missing). */
    public static String param(HttpServletRequest request, String name) {
        return esc(request.getParameter(name));
    }
}
