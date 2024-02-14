import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Создаём объект Scanner, который позволяет считывать ввод
        System.out.println("Введите пример в формате 'число операция число':");
        // Условие №1. Данные передаются в одну строку!
        String input = sc.nextLine(); // Создаем переменную, в которую пользователь должен ввести пример.
        System.out.println("Output: " + calc(input)); // Выводим результат. Вызывается метод calc(input): Сначала выполняется вычисление в методе calc а затем выводится результат.
    }

    public static String calc(String input) {
        String[] parts = input.split(" "); // Разбиваем введенный пример на части (число, оператор, число) по пробелу

        // Условие №7. При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
        if (parts.length != 3) {
            throw new IllegalArgumentException("Ошибка: Пример должен быть вида 'число операция число'.");
        }

        String strNumber1 = parts[0];
        String strNumber2 = parts[2];

        // Если обе переменные содержат римские значения, то isRoman = true аналогично с isArabic
        boolean isRoman = isRomanNumerals(strNumber1) && isRomanNumerals(strNumber2);
        boolean isArabic = isArabicNumerals(strNumber1) && isArabicNumerals(strNumber2);

        // Условие №5. Калькулятор умеет работать только с арабскими или римскими цифрами одновременно,
        // при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.
        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Ошибка: Цифры должны быть арабскими или римскими.");
        }

        int number1, number2, result;
        char operator = parts[1].charAt(0);

        if (isRoman){
            number1 = romanToArabic(strNumber1);
            number2 = romanToArabic(strNumber2);
        } else {
            // Integer.parseInt позволяет преобразовать символ в целое число.
            number1 = Integer.parseInt(strNumber1);
            number2 = Integer.parseInt(strNumber2);
        }

        // Условие №3. Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
        if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
            throw new IllegalArgumentException("Ошибка: Числа должны быть в диапазоне от 1 до 10.");
        }

        // Условие №1. Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b
        switch (operator) {
            case '+':
                result = number1 + number2;
                break;
            case '-':
                result = number1 - number2;
                break;
            case '*':
                result = number1 * number2;
                break;
            case '/':
                if (number1 == 0 || number2 == 0) {
                    throw new ArithmeticException("Ошибка: Нельзя делить на ноль.");
                }
                result = number1 / number2;
                break;
                // Условие №8. При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.
            default:
                throw new UnsupportedOperationException("Ошибка: Неизвестная операция.");
        }

        // Условие №6. При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
        if (isArabic){
            return String.valueOf(result);
        }
        else return arabicToRoman(result);
    }

    private static boolean isRomanNumerals(String str) {
        return str.matches("[IVXLC]+"); // Содержит ли строка такие символы, если да, то true | + означает, что в строке может быть два символа
    }

    private static boolean isArabicNumerals(String str) {
        return str.matches("-?\\d+"); // Содержит ли строка 0-9 | - (минус), что число может быть отрицательное
    }

    public static String[] romanNumerals = { // Создаём массив, которые содержит в себе римские числа от 1 до 100
            "Z", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
            "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
            "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI","LXXXII","LXXXIII","LXXXIV","LXXXV","LXXXVI","LXXXVII","LXXXVIII","LXXXIX","XC",
            "XCI","XCII","XCIII","XCIV","XCV","XCVI","XCVII","XCVIII","XCIX","C"
    };

    public static int romanToArabic(String romanNumeral){
        for (int i = 0; i < romanNumerals.length; i++){
            if (romanNumerals[i].equals(romanNumeral)) {
                return i;
            }
        }
        return 0;
    }

    public static String arabicToRoman(int arabicNumeral){
        if (arabicNumeral <= 0){
            throw new IllegalArgumentException("Результат операций римских чисел не может быть меньше или равен нулю");
        }
        return romanNumerals[arabicNumeral];
    }
}