package com.example.listasmart.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFS = "listasmart";

    public static Long getIdUsuario(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(
                        PREFS,
                        Context.MODE_PRIVATE
                );

        return prefs.getLong(
                "id_usuario",
                -1
        );
    }

    public static boolean usuarioLogado(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(
                        PREFS,
                        Context.MODE_PRIVATE
                );

        return prefs.getBoolean(
                "logado",
                false
        );
    }
}