package news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NewsList {
    private ArrayList<News> allNews;
    public String news[]={
            "НОВЫЕ ПРАВИЛА ПОДАЧИ ЗАЯВКИ НА FX",
            "Открыта запись ко второму психологу на ближайшее время",
            "Психологический квест для первокурсников",
            "о переносе занятий лектора ШПМ Сарсеновой Феризат Саламаткызы",
            "Об отмене и переносе занятий Исмаиловой Д.А. \"Введение в нефтегазовый инжиниринг\"\n",
            "об отмене занятий профессора ШПМ Байшемирова Жарасбека Дуйсембековича",
            "о переносе занятий сениор-лектора ШПМ Тұрсынжановой Әлии Айбарқызы",
            "Об отработке занятий ассоциированного профессора ШМиЗТ Воронкова В.В.",
            "о переносе занятий сениор-лектора ШПМ Өстеміровой Ұлданы Бектемірқыз",
            "об отработке занятий сениор-лектора ШПМ Рысбаевой Назерке Болатбекқызы"
    };

    public NewsList() {
        this.allNews = new ArrayList<>();
    }

    public void add(News n){
        allNews.add(n);
    }

    public void sort(){
        Collections.sort(allNews);
    }

    public ArrayList<News> sort(Comparator<News> c){
        if (allNews != null) {
            Collections.sort(allNews, c);
        }
        return allNews;
    }

    @Override
    public String toString(){
        String s = "";
        for (News n : allNews){
            s += n.toString() + "\n";
        }
        return s;
    }

}
