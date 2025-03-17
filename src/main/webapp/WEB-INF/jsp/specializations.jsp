<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список специализаций</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }

        h1 {
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            margin-bottom: 10px;
            padding: 10px 15px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: background-color 0.2s ease-in-out;
        }

        li:hover {
            background-color: #eaeaea;
        }

        a {
            text-decoration: none;
            color: #333;
            font-size: 18px;
        }

        a:hover {
            color: #007bff;
        }

        .container {
            width: 80%;
            margin: 0 auto;
        }

        .btn-back {
            display: inline-block;
            margin: 20px auto;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            width: 200px;
        }

        .btn-back:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Список специализаций</h1>
    <ul id="specialization-list">
        <!-- Данные будут вставлены сюда -->
    </ul>

    <a class="btn-back" href="/home">Назад на главную</a>
</div>

<script>
    // Функция для загрузки специализаций
    async function fetchSpecializations() {
        try {
            // Делаем запрос к API
            const response = await fetch('http://localhost:8080/api/specializations?page=0&perPage=10&sort=name&order=ASC');

            if (!response.ok) {
                throw new Error('Ошибка загрузки специализаций');
            }

            const data = await response.json();  // Получаем данные в формате JSON
            const listElement = document.getElementById('specialization-list');

            // Очищаем список перед вставкой новых данных
            listElement.innerHTML = '';

            // Если данные есть, выводим их на страницу
            if (data.content && data.content.length > 0) {
                data.content.forEach(specialization => {
                    // Создаём элемент списка для каждой специализации
                    const li = document.createElement('li');
                    const link = document.createElement('a');
                    link.href = `alumni.html?specialization=${specialization.name}`;
                    link.textContent = specialization.name;
                    li.appendChild(link);
                    listElement.appendChild(li);
                });
            } else {
                // Если специализаций нет, выводим соответствующее сообщение
                listElement.innerHTML = '<li>Нет доступных специализаций</li>';
            }
        } catch (error) {
            console.error('Ошибка:', error);
            document.getElementById('specialization-list').innerHTML = '<li>Ошибка при загрузке данных</li>';
        }
    }

    // Загружаем специализации при открытии страницы
    fetchSpecializations();
</script>
</body>
</html>