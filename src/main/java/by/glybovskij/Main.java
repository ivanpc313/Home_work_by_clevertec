package by.glybovskij;

import by.glybovskij.model.Animal;
import by.glybovskij.model.Car;
import by.glybovskij.model.Flower;
import by.glybovskij.model.House;
import by.glybovskij.model.Person;
import by.glybovskij.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
       task1();
       task2();
       task3();
       task4();
       task5();
       task6();
       task7();
       task8();
       task9();
       task10();
       task11();
       task12();
       task13();
       task14();
       task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        List<Animal> myZoo = animals.stream()
                .filter(Animal -> Animal.getAge() >= 10)
                .filter(Animal -> Animal.getAge() <= 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .skip(14)
                .limit(7)
                .collect(Collectors.toList());
        System.out.print(myZoo);
    }

    private static void task2() throws IOException {

      /*  Задача №2 - Отобрать всех животных из Японии (Japanese) и записать породу UPPER_CASE в если пол Female
        преобразовать к строкам породы животных и вывести в консоль */

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(Animal -> "Japanese".equals(Animal.getOrigin()))
                .peek(Animal -> Animal.setBread(Animal.getBread().toUpperCase()))
                .filter(Animal -> "Female".equals(Animal.getGender()))
                .forEach(System.out::println);
    }


    private static void task3() throws IOException {

        /*Задача №3 -Отобрать всех животных старше 30 лет и вывести все страны происхождения без дубликатов начинающиеся на "A" */

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(Animal -> Animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.contains("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {

        /* Задача №4 - Подсчитать количество всех животных пола = Female. Вывести в консоль */

        List<Animal> animals = Util.getAnimals();
        List<Animal> female = animals.stream()
                .filter(Animal -> "Female".equals(Animal.getGender()))
                .toList();

        int total = female.size();
        System.out.println(total);
    }

    private static void task5() throws IOException {

        /* Задача №5 - Взять всех животных возрастом 20 - 30 лет. Есть ли среди них хоть один из страны Венгрия (Hungarian)?
        Ответ вывести в консоль */

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(Animal -> Animal.getAge() >= 20)
                .filter(Animal -> Animal.getAge() <= 30)
                .filter(Animal -> "Hungarian".equals(Animal.getOrigin()))
                .forEach(System.out::println);
    }

    private static void task6() throws IOException {

      /*  Задача №6 - Взять всех животных. Все ли они пола Male и Female ?
          Ответ вывести в консоль */
        List<Animal> animals = Util.getAnimals();
        boolean answer = animals.stream()
                .allMatch(x -> "Male".equals(x.getGender()) || "Female".equals(x.getGender()));
        System.out.println(answer);
    }

    private static void task7() throws IOException {

        /*Задача №7 -Взять всех животных. Узнать что ни одно из них не имеет страну происхождения Oceania.
        Ответ вывести в консоль */

        List<Animal> animals = Util.getAnimals();
        boolean answer = animals.stream()
                .anyMatch(x -> "Oceania".equals(x.getOrigin()));
        System.out.println(answer);
    }

    private static void task8() throws IOException {

        /* Задача №8 - Взять всех животных. Отсортировать их породу в стандартном порядке и взять первые 100.
        Вывести в консоль возраст самого старого животного */

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(new BreadComparator())
                .limit(100)
                .map(Animal::getAge)
                .reduce(Math::max)
                .ifPresent(System.out::println);
    }

    static class BreadComparator implements Comparator<Animal> {

        @Override
        public int compare(Animal o1, Animal o2) {
            return o1.getBread().compareTo(o2.getBread());
        }
    }


    private static void task9() throws IOException {

         /* Задача №9 - Взять всех животных. Преобразовать их в породы, а породы в []char
        Вывести в консоль длину самого короткого массива */

        List<Animal> animals = Util.getAnimals();
        Optional<char[]> minLength = animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .reduce((bread1, bread2) -> bread1.length < bread2.length ? bread1 : bread2);
        minLength.ifPresent(x -> System.out.println(x.length));
    }

    private static void task10() throws IOException {

        /* Задача №10 - Взять всех животных. Подсчитать суммарный возраст всех животных. Вывести результат в консоль */

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .parallel()
                .map(Animal::getAge)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    private static void task11() throws IOException {

        /* Задача №11 -Взять всех животных. Подсчитать средний возраст всех животных из индонезии (Indonesian). Вывести результат в консоль */

        List<Animal> animals = Util.getAnimals();
        OptionalDouble averageAge = animals.stream()
                .filter(x -> "Indonesian".equals(x.getOrigin()))
                .map(Animal::getAge)
                .mapToInt(Integer::intValue)
                .average();
        averageAge.ifPresent(System.out::println);
    }

    private static void task12() throws IOException {

        /* Задача №12 - Во Французский легион принимают людей со всего света, но есть отбор по полу (только мужчины)
        возраст от 18 до 27 лет. Преимущество отдаётся людям военной категории 1, на втором месте - военная категория 2,
        и на третьем месте военная категория 3. Отсортировать всех подходящих кандидатов в порядке их
        приоритета по военной категории. Однако взять на обучение академия может только 200 человек. Вывести их в консоль. */

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(18);
        LocalDate endDate = now.minusYears(27);

        List<Person> people = Util.getPersons();
        people.stream()
                .filter(x -> "Male".equals(x.getGender()))
                .filter(x -> startDate.isAfter(x.getDateOfBirth()))
                .filter(x -> endDate.isBefore(x.getDateOfBirth()))
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        //        Продолжить...
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        //        Продолжить...
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}