# Favorite Recipes

Favorite Recipes – это веб-приложение для хранения, парсинга и управления рецептами. Приложение позволяет пользователям создавать, редактировать и организовывать рецепты, а также быстро получать доступ к часто используемым. Основной функционал включает автоматический парсинг рецептов с URL с помощью Spoonacular API и последующее редактирование в формате Markdown через [@toast-ui/editor](https://github.com/nhn/tui.editor).

---

# Favorite Recipes

Favorite Recipes is a web application for storing, parsing, and managing recipes. The app allows users to create, edit, and organize recipes, as well as quickly access frequently used ones. Its main functionality includes automatic parsing of recipes from a URL using the Spoonacular API, with subsequent editing in Markdown via [@toast-ui/editor](https://github.com/nhn/tui.editor).

---

## Особенности и юзкейсы / Features and Use Cases

### Основные возможности / Key Features

- **Создание рецепта по URL / Create a Recipe by URL:**
    - Пользователь нажимает кнопку «Создать новый рецепт» и вводит URL.
    - При нажатии «Попробовать распарсить» приложение отправляет запрос к Spoonacular API, заполняя черновик рецепта полученными данными.
    -
    - The user clicks the "Create New Recipe" button and enters a URL.
    - When they click "Try Parsing," the application sends a request to the Spoonacular API to pre-fill a draft recipe with the extracted data.

- **Редактирование рецепта / Recipe Editing:**
    - Рецепт загружается в [@toast-ui/editor](https://github.com/nhn/tui.editor) в формате Markdown.
    - Пользователь может вручную корректировать рецепт: добавлять ингредиенты, инструкции, изображения и т.д.
    -
    - The recipe is loaded into the @toast-ui/editor in Markdown format.
    - Users can manually edit the recipe: add ingredients, instructions, images, etc.

- **Управление статусами рецептов / Recipe Status Management:**
    - Рецепты сохраняются как черновики (`DRAFT`) до окончательной публикации.
    - Пользователь может легко разделять рецепты на те, которые готовил, и те, которые не готовил, для быстрого доступа к часто используемым.
    -
    - Recipes are initially saved as drafts (`DRAFT`) until published.
    - Users can quickly separate recipes into those they have cooked and those they haven't, making frequently prepared dishes easily accessible.

- **Категоризация и поиск / Categorization and Search:**
    - Фильтрация по заголовкам, категориям (например, «выпечка», «мясо» и др.) и тегам.
    - Сортировка рецептов по количеству приготовлений, чтобы любимые рецепты были на виду.
    -
    - Filter recipes by title, category (e.g., "Baking", "Meat", etc.), and tags.
    - Sort recipes by the number of times they have been cooked, ensuring that favorite recipes are easily accessible.

- **Интеграция с Spoonacular API / Spoonacular API Integration:**
    - Пользователи могут регистрироваться в приложении и сохранять свои API-ключи с [Spoonacular API](https://spoonacular.com/food-api).
    - Ключи позволяют автоматически извлекать данные рецептов (название, ингредиенты, изображения, инструкции и т.д.) с различных сайтов.
    -
    - Users can register on the platform and store their Spoonacular API keys.
    - With their keys, they can leverage automatic extraction of recipe data (title, ingredients, images, instructions, etc.) from various websites.

### Юзкейсы / Use Cases

1. **Создание черновика с URL (мобильный сценарий) / Creating a Draft with a URL (Mobile Scenario):**
    - Пользователь получает ссылку от друга, открывает мобильное приложение, вводит URL, и рецепт сохраняется в статусе "DRAFT".
    -
    - A user receives a recipe link from a friend, opens the mobile app, enters the URL, and the recipe is saved with the status "DRAFT."

2. **Редактирование на десктопе / Editing on Desktop:**
    - Пользователь переходит в список черновиков, выбирает рецепт и нажимает «Попробовать распарсить» для автозаполнения деталей, а затем редактирует его по необходимости.
    -
    - The user opens the list of drafts on a desktop, selects a recipe, and clicks "Try Parsing" to auto-fill recipe details, then edits as needed.

3. **Повторное приготовление / Repeated Cooking:**
    - Пользователь может просматривать рецепты, которые уже готовил, для быстрого повторного приготовления (например, "тесто для пиццы"), а также рецепты, которые ещё не пробовал.
    -
    - Users can view recipes they have cooked before for quick re-cooking (e.g., "Pizza Dough") as well as recipes they haven't tried yet.

---

## Технологический стек / Technology Stack

- **Backend:**
    - **Java 17** – современный язык программирования.
    - **Spring Boot** – для быстрого создания приложения.
    - **Spring Security** – для обеспечения безопасности и аутентификации.
    - **Spring Data** – для упрощения работы с базой данных.
    - **Lombok** – для сокращения шаблонного кода.
    - **PostgreSQL** – в качестве основной реляционной базы данных.

- **Frontend:**
    - **Vue.js** – для создания современного и реактивного пользовательского интерфейса.
    - **Bootstrap** – для создания адаптивных и привлекательных компонентов UI.
    - **Axios** – для выполнения HTTP-запросов между frontend и backend.

- **Deployment and Proxy:**
    - **Caddy** – в роли веб-сервера и реверс-прокси, обеспечивающего безопасное и эффективное развертывание приложения.

---

## Установка и запуск / Installation and Running

### Backend Setup

1. **Клонирование репозитория / Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/favorite-recipes.git
   cd favorite-recipes/backend-api

## Конфигурация базы данных / Database Configuration:

Обновите файл `application.properties` (или `application.yml`) с данными для подключения к PostgreSQL.

Для разработки рекомендуется установить:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

Это приведёт к пересозданию таблиц при каждом запуске приложения (не использовать в production).

Update the `application.properties` (or `application.yml`) file with your PostgreSQL connection details.

For development, set:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

This setting will drop and recreate your tables on each application start (do not use in production).

---

## Запуск приложения / Run the Application:

```bash
./mvnw spring-boot:run
```

---

## Frontend Setup

### Переход в директорию frontend / Navigate to the Frontend Directory:

```bash
cd ../frontend
```

### Установка зависимостей и запуск сервера / Install Dependencies and Start the Server:

```bash
npm install
npm run serve
```

**Примечание / Note:** Если приложение запускается на порту 8082, и он занят, убедитесь, что вы завершили все процессы, использующие этот порт.

- **На Windows / On Windows:**

  ```bash
  netstat -ano | findstr :8082
  taskkill /PID <PID> /F
  ```

- **На Linux/macOS:**

  ```bash
  lsof -i :8082
  kill -9 <PID>
  ```

---

## Интеграция с Spoonacular API / Spoonacular API Integration

Зарегистрируйтесь на [Spoonacular API](https://spoonacular.com/food-api) для получения бесплатного API-ключа.

В приложении пользователи могут сохранять свои API-ключи для автоматического парсинга рецептов по URL.

Сервис использует API-ключ для извлечения данных рецепта (название, ингредиенты, изображения, инструкции и т.д.) и предварительного заполнения черновиков.

Register on [Spoonacular API](https://spoonacular.com/food-api) to obtain a free API key.

Within the application, users can store their API keys to automatically parse recipe data from URLs.

The service utilizes the API key to extract recipe details (title, ingredients, images, instructions, etc.) and pre-populate draft recipes.

---

## Вклад в проект / Contributing

Мы приветствуем вклад в развитие проекта! Если у вас есть идеи для улучшений, вы обнаружили баг или хотите внести изменения, пожалуйста, создавайте issues или отправляйте pull requests.

We welcome contributions to the project! If you have ideas for improvements, discover bugs, or want to enhance the project, please open an issue or submit a pull request.

---

## Лицензия / License

Этот проект распространяется под лицензией **MIT License**.

This project is licensed under the **MIT License**.

---

Happy cooking! / Приятного аппетита!
