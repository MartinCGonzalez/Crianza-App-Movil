package com.crianza.validaciones;

import com.crianza.enums.TipoAlimento;
import com.crianza.exceptions.FormatoFechaNoValido;
import com.crianza.exceptions.LargoDecimalException;
import com.crianza.exceptions.LargoNumeroException;
import com.crianza.exceptions.NombreAlimentoException;
import com.crianza.exceptions.NumeroNegativoException;
import com.crianza.exceptions.ObservacionException;
import com.crianza.exceptions.FechaFuturoException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ValidacionDatos {

    private ValidacionDatos() {

    }

    public static boolean validarAlimento(BigDecimal costoUnit, BigDecimal cantidad) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionCosto(costoUnit) && validar.revisionCantidad(cantidad))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarActualizacion(BigDecimal costoUnit, BigDecimal cantidad) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionCantidadActualizacion(cantidad) && validar.revisionCosto(costoUnit)) {
                salida = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarAgregar(BigDecimal cantidad) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionCantidadActualizacion(cantidad)) {
                salida = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarNombre(String nombre) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionNombreAlimento(nombre))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarConsumoAlimento(BigDecimal cantidad, Date date) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionCantidad(cantidad) && validar.revisionFecha(date))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarEventoClinico(String obs, Date date) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionObservacion(obs) && validar.revisionFecha(date))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarEventoClinico2(Date fechaHasta, Date fechaDesde, String obs) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionFecha(fechaHasta) && validar.revisionFecha(fechaDesde) && validar.revisionObservacion(obs))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;

    }

    public static boolean validarTernero(BigDecimal cantidad, Date date) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionPeso(cantidad) && validar.revisionFecha(date))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    public static boolean validarPeso(BigDecimal cantidad, Date date) {

        ValidacionDatos validar = new ValidacionDatos();
        boolean salida = false;
        try {
            if (validar.revisionFecha(date) && validar.revisionPeso(cantidad))
                salida = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    // -----------------------
    // ---METODOS PRIMARIOS---
    // -----------------------

    public static boolean verificarDecimal(BigDecimal cantidad) {

        // FUNCIONANDO

        boolean salida;

        String text = String.valueOf(cantidad);
        int decimales = text.indexOf(".");
        int largoDeciales = decimales < 0 ? 0 : text.length() - decimales - 1;

        if (largoDeciales > 2) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;
    }

    public static boolean verificarNumero(BigDecimal num) {

        // FUNCIONANDO
        // Tomo el largo de los enteros, si es > exception
        boolean salida = true;

        int largo = num.signum() == 0 ? 1 : num.precision() - num.scale();

        if (largo > 5) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;

    }

    public static boolean verificarPeso(BigDecimal num) {

        // FUNCIONANDO
        // Tomo el largo de los enteros, si es > exception
        boolean salida = true;

        int largo = num.signum() == 0 ? 1 : num.precision() - num.scale();

        if (largo > 4) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;

    }

    public static boolean verificarPositivo(BigDecimal num) {

        // FUNCIONANDO
        boolean salida;

        Double numeroIngreso = num.doubleValue();

        if (numeroIngreso > 0) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;

    }

    public static boolean verificarMayorCero(BigDecimal num) {

        // FUNCIONANDO
        boolean salida;

        Long numeroIngreso = num.longValue();

        if (numeroIngreso >= 0) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;

    }

    public static boolean verificarFecha(Date date) {

        // FUNCIONANDO
        boolean salida;

        Date hoy = new Date();

        Date fecha1 = date;

        Calendar esteAño = Calendar.getInstance();
        esteAño.setTime(hoy);
        int corrienteAño = esteAño.get(Calendar.YEAR);

        Calendar pasadoAño = Calendar.getInstance();
        pasadoAño.setTime(fecha1);
        pasadoAño.add(Calendar.YEAR, 0);
        int pasado = pasadoAño.get(Calendar.YEAR);

        if (fecha1.after(hoy)) {
            salida = true;
        } else if (pasado < corrienteAño) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;
    }

    public static boolean verificarFormatoFecha(Date date) {

        boolean salida;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
            sdf.format(date);
            salida = false;
        } catch (Exception e) {
            salida = true;
        }
        return salida;

    }


    // -------------------------
    // ---METODOS SECUNDARIOS---
    // -------------------------

    public static boolean verificarObservaciones(String obs) {

        boolean salida;

        if (obs.length() > 250) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;
    }

    private boolean revisionCosto(BigDecimal costoUnit)
            throws NumeroNegativoException, LargoDecimalException, LargoNumeroException {
        // Verifico sea mayor a cero
        if (!verificarPositivo(costoUnit)) {
            throw new NumeroNegativoException();
        }

        // Verifico no tenga mas de dos decimales
        if (verificarDecimal(costoUnit)) {
            throw new LargoDecimalException();
        }

        // Verifico el numero no tenga mas de 5 digitos
        if (verificarNumero(costoUnit)) {
            throw new LargoNumeroException();
        }
        return true;
    }

    private boolean revisionCantidad(BigDecimal cantidad)
            throws NumeroNegativoException, LargoDecimalException, LargoNumeroException {

        if (!verificarPositivo(cantidad)) {
            throw new NumeroNegativoException();
        }

        // Verifico no tenga mas de dos decimales
        if (verificarDecimal(cantidad)) {
            throw new LargoDecimalException();
        }

        // Verifico sea mayor a cero
        if (verificarNumero(cantidad)) {
            throw new LargoNumeroException();
        }
        return true;
    }

    private boolean revisionPeso(BigDecimal cantidad)
            throws NumeroNegativoException, LargoDecimalException, LargoNumeroException {

        if (!verificarPositivo(cantidad)) {
            throw new NumeroNegativoException();
        }

        // Verifico no tenga mas de dos decimales
        if (verificarDecimal(cantidad)) {
            throw new LargoDecimalException();
        }

        // Verifico sea mayor a cero
        if (verificarPeso(cantidad)) {
            throw new LargoNumeroException();
        }
        return true;
    }

    private boolean revisionCantidadActualizacion(BigDecimal cantidad)
            throws NumeroNegativoException, LargoDecimalException, LargoNumeroException {

        if (!verificarMayorCero(cantidad)) {
            throw new NumeroNegativoException();
        }
        // Verifico no tenga mas de dos decimales
        if (verificarDecimal(cantidad)) {
            throw new LargoDecimalException();
        }

        // Verifico sea mayor a cero
        if (verificarNumero(cantidad)) {
            throw new LargoNumeroException();
        }
        return true;

    }

    private boolean revisionNombreAlimento(String nombre) throws NombreAlimentoException {

        if (verificarNombre(nombre)) {
            throw new NombreAlimentoException();
        }

        return true;
    }

    private boolean revisionFecha(Date date) throws FechaFuturoException, FormatoFechaNoValido {

        if (verificarFecha(date)) {
            throw new FechaFuturoException();
        }

        if (verificarFormatoFecha(date)) {
            throw new FormatoFechaNoValido();
        }
        return true;
    }

    private boolean revisionObservacion(String obs) throws ObservacionException {

        if (verificarObservaciones(obs)) {
            throw new ObservacionException();
        }
        return true;
    }

    private boolean verificarNombre(String nombre) {

        // FUNCIONANDO
        // Esto valida que el Alimento ingresado sea un valor del enum
        String t1 = TipoAlimento.LECHE.toString();
        String t2 = TipoAlimento.CALOSTRO_FORZADO.toString();
        String t3 = TipoAlimento.CALOSTRO_NATURAL.toString();
        String t4 = TipoAlimento.INICIADOR.toString();
        String t5 = TipoAlimento.RACION.toString();
        String t6 = TipoAlimento.SUSTITUTO_LACTEO.toString();

        boolean salida;

        String text = String.valueOf(nombre);

        if (text.equals(t1)) {
            salida = false;
        } else if (text.equals((t2))) {
            salida = false;
        } else if (text.equals(t3)) {
            salida = false;
        } else if (text.equals(t4)) {
            salida = false;
        } else if (text.equals(t5)) {
            salida = false;
        } else if (text.equals(t6)) {
            salida = false;
        } else {
            salida = true;
        }
        return salida;
    }

}
