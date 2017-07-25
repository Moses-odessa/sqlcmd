Консольное приложение для подключения к базе данных PostgreSQL, просмотра, добавления и редактирования данных.
Запуск из класа Main
Параметры подключения сервера баз данных в файле src/main/resources/postgres.properties

СПИСОК КОММАНД:

connect|database|username|userpassword - подключение к базе данных,
	где database - название базы данных, username - имя пользователя, userpassword - пароль

tables - вывод списка всех таблиц в подключенной базе данных.

create|tablename|column1|column2|column3|... - создание новой таблицы с заданными полями,
	где tablename - имя таблицы; column1, column2, column3 и т.д - названия ее полей (не больше ста)

drop|tablename - удаление таблицы из базы данных,
	где tablename - имя удаляемой таблицы

clear|tablename - очистка всех данных в таблице,
	где tablename - имя очищаемой таблицы

show|tablename или show|tablename|sortcolumn - вывод содержимого таблицы,
	где tablename - имя нужной таблицы, sortcolumn - имя колонки по которой будет отсортирована таблица
	(если опущено - без сортировки)

insert|tablename|column1|value1|column2|value2|... - добавление записи в таблицу,
	где tablename - имя таблицы,
	column1, column2, и т.д - названия записываемых полей (не больше ста)
	value1, value2, и т.д - соответственно записываемые значения

update|tablename|criteriacolumn|criteriavalue|setcolumn|setvalue - обновление записей в таблице,
	где tablename - имя таблицы, criteriacolumn, criteriavalue - колонка и значение условия отбора,
	setcolumn, setvalue - обновляемая колонка и новое значение для нее

delete|tablename|criteriacolumn|criteriavalue - удаление записей в таблице,
	где tablename - имя таблицы, criteriacolumn, criteriavalue - колонка и значение условия отбора

help или help|command - вызов справки,
	по всем коммандам (при вызове без параметров) или по комманде command