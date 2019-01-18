# vert.x против классической многопоточности в JVM

## Описание
В проекте показывается как такие классические задачи на многопоточность как “Читатели и писатели” или “Обедающие философы”, 
благодаря отходу от классической Java Concurrency с блокировками, могут быть элегантно решены на _vert.x_: полиглотном 
фреймворке для создания реактивных высоконагруженных приложений на JVM. 

## Кому может быть интересно
 - Программистам на языках работающих под JVM: Java, JavaScript, Groovy, Ruby, Ceylon, Scala, Kotlin или интересующимся этими языками 
 - Разработчикам высоконагруженных систем на JVM и не только
 - Разработчикам, которые устали от стандартных стеков на Spring или JEE и хотят попробовать что-то новое и прикольное 
 - Веб-разработчикам на JVM и не только 
 - Java-программистам, уставшим от стандартного java concurrency с блокировками
 
## Что не так со стандартным java concurrеncy?
* сложно, мудрено
* дорого переключать потоки
* для веба подход поток на коннекшен больше не катит

## Какие есть альтернативные подходы?
Actors Model, CSP, STM, fibers, coroutines, continuations

## Минимум знаний по https://vertx.io необходимый для понимания: 
 - основные принципы: events, eventloop, async, nonblocking
 - основные сущности: vertical, eventbus, shared data 
 - подход к конкарренси в vertx
 
## В проекте разбираются пять классических задач на многопоточность
* Задача о читателях и писателях 
* Производители и потребители
* Спящий парикмахер
* Обедающие философы
* Задания и работники

Формат разбора задач: 
 1. постановка задачи
 1. классическое решение с блокировками
 1. элегантное решение на вертексе

## Ссылки
https://vertx.io
https://www.kgeorgiy.info/courses/java-advanced/lectures/threads-2.html
https://alexeykalina.github.io/technologies/concurrency.html
https://engineering.universe.com/introduction-to-concurrency-models-with-ruby-part-i-550d0dbb970
https://engineering.universe.com/introduction-to-concurrency-models-with-ruby-part-ii-c39c7e612bed
