# vert.x против классической многопоточности в JVM

## Описание
В проекте показывается как такие классические задачи на многопоточность как “Читатели и писатели” или “Обедающие философы”, 
благодаря отходу от классической Java Concurrency с блокировками, могут быть решены на _vert.x_ без блокировок: полиглотном 
фреймворке для создания реактивных высоконагруженных приложений на JVM. 

## Кому может быть интересно
 - Программистам на языках работающих под JVM: Java, JavaScript, Groovy, Ruby, Ceylon, Scala, Kotlin или интересующимся этими языками 
 - Разработчикам высоконагруженных систем на JVM и не только
 - Разработчикам, которые устали от стандартных java стеков на Spring или JEE и хотят попробовать что-то современное и классное 
 - Веб-разработчикам на JVM и не только 
 - Java-программистам, уставшим от стандартного java concurrency с блокировками
 
## Что не так со стандартным java concurrеncy?
* сложно, мудрено
* не композабл
* могут стать hot spot
* дедлоки, лайвлоки, голодание, инверсия приоритетов, нарушение инкапсуляции, конвоирование
* дорого переключать потоки
* для веба подход "отдельный поток на каждый запрос" не подходит для высоконагруженных приложений

## Какие есть альтернативные подходы?
Actors Model, CSP, STM, fibers, coroutines, continuations, функциональное программирование, Erlang

## Минимум знаний по https://vertx.io необходимый для понимания: 
 - основные принципы: events, eventloop, async, nonblocking
 - основные сущности: vertical, eventbus, shared data 
 - подход к конкарренси в vertx
 
## В проекте разбираются пять классических задач на многопоточность
* Производители и потребители - V
* Задача о читателях и писателях (с приоритетеом писателя) - V
* Обедающие философы - V
* Спящий парикмахер - no
* Задания и работники - no

Формат разбора задач: 
 1. постановка задачи
 1. классическое решение с блокировками
 1. решение на vert.x без блокировок
 1. (под вопросом) сравнение производиельности, результаты JMH  

## Ссылки
https://vertx.io
TODO add links to Actors Model, CSP, STM, fibers, coroutines, continuations
https://en.wikipedia.org/wiki/Producer–consumer_problem
https://en.wikipedia.org/wiki/Readers–writers_problem
https://en.wikipedia.org/wiki/Dining_philosophers_problem

## TODO remove the following links later
https://www.kgeorgiy.info/courses/java-advanced/lectures/threads-2.html
https://alexeykalina.github.io/technologies/concurrency.html
https://engineering.universe.com/introduction-to-concurrency-models-with-ruby-part-i-550d0dbb970
https://engineering.universe.com/introduction-to-concurrency-models-with-ruby-part-ii-c39c7e612bed
http://highload.guide/blog/DBMS-for-data-in-RAM.html - начиная со слайда "Подходы к конкарренсу", и далее 
Конвоирование
