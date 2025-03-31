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
            background-color: #f5f5f5;
            color: #333;
        }
        h1 {
            text-align: center;
        }
        .specialization-container {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: center;
        }
        .specialization {
            padding: 15px 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: background-color 0.2s ease-in-out;
        }
        .specialization:hover {
            background-color: #eaeaea;
        }
        a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
        a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Список специализаций</h1>
<div class="specialization-container" id="specialization-container">
</div>

<script>
    async function fetchSpecializations() {
        try {
            const response = await fetch('http://localhost:8080/api/specializations');
            if (!response.ok) throw new Error('Ошибка загрузки специализаций');

            const data = await response.json();
            const container = document.getElementById('specialization-container');
            container.innerHTML = '';

            if (data.length > 0) {
                data.forEach(specialization => {
                    const div = document.createElement('div');
                    div.className = 'specialization';

                    const link = document.createElement('a');
                    link.href = alumni.jsp?specialization=${specialization.id};
                    link.textContent = specialization.name;

                    div.appendChild(link);
                    container.appendChild(div);
                });
            } else {
                container.innerHTML = '<p>Нет доступных специализаций</p>';
            }
        } catch (error) {
            console.error('Ошибка:', error);
            document.getElementById('specialization-container').innerHTML = '<p>Ошибка при загрузке данных</p>';
        }
    }

    fetchSpecializations();
</script>

</body>
</html>