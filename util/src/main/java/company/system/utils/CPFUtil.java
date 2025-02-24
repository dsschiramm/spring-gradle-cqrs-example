package company.system.utils;

import java.util.InputMismatchException;

public class CPFUtil {

    public static String format(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String clean(String cpf) {

        if (cpf == null)
            return null;

        return cpf.trim()
                .replace(".", "")
                .replace("-", "");
    }

    public static boolean isValid(String value) {

        if (value == null)
            return false;

        String cpf = value.replace(".", "").replace("-", "");

        if (cpf.length() != 11)
            return false;

        if (cpf.matches("(\\d)\\1{10}"))
            return false;

        try {
            int soma = 0;
            int peso = 10;

            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cpf.charAt(i));
                soma += num * peso;
                peso--;
            }

            int resto = soma % 11;
            int digito1 = (resto < 2) ? 0 : 11 - resto;

            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++) {
                int num = Character.getNumericValue(cpf.charAt(i));
                soma += num * peso;
                peso--;
            }

            resto = soma % 11;
            int digito2 = (resto < 2) ? 0 : 11 - resto;

            return (digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                    digito2 == Character.getNumericValue(cpf.charAt(10)));

        } catch (InputMismatchException e) {
            return false;
        }
    }
}
