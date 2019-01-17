vert.x против классической многопоточности в JVM

Описание
В своем докладе я покажу как такие классические задачи на многопоточность как “Читатели и писатели” или “Обедающие философы”, благодаря отходу от классической Java Concurrency с блокировками, могут быть элегантно решены на vert.x: полиглотном фреймворке для создания реактивных высоконагруженных приложений на JVM. 

ЦА
Программисты на языках работающих под JVM (Java, JavaScript, Groovy, Ruby, Ceylon, Scala, Kotlin) или интересующиеся 
разработчики высоконагруженных систем (на JVM и не только)
разработчики которые устали от стандартных стеков (на Spring или JEE), 
веб-разработчики (на JVM и не только), 
java-программисты, уставшие от стандартного java concurrency с блокировками


Расширенные тезисы
Расскажу что не так со стандартным java concurrеncy
* сложно, мудрено
* дорого переключать потоки
* для веба подход поток на коннекшен больше не катит
Какие есть подходы (перечислю альтернативы Actors, CSP, STM, fibers, coroutines, continuations)
пару слов о фреймворке vertx: основные принципы (events, eventloop, async, nonblocking), основные сущности: vertical, eventbus, shared data 
Кратко подход к конкарренси в vertx
Далее разбор пяти (если влезут конечно) классических задач на многопоточность в формате: постановка задачи, классическое решение с блокировками, элегантное решение на вертексе:
* Задача о читателях и писателях 
* Производители и потребители
* Спящий парикмахер
* Обедающие философы
* Задания и работники
Выводы



Ссылки
https://www.kgeorgiy.info/courses/java-advanced/lectures/threads-2.html
https://alexeykalina.github.io/technologies/concurrency.html
https://engineering.universe.com/introduction-to-concurrency-models-with-ruby-part-i-550d0dbb970
https://engineering.universe.com/introduction-to-concurrency-models-with-ruby-part-ii-c39c7e612bed
