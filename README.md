# Calculator Project

A full-stack calculator with an Angular frontend and a Java Spring Boot 2 backend.

## Run the backend

Install Maven, then from `backend` run:

```powershell
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

## Run the frontend

From `frontend` run:

```powershell
npm install
npm start
```

Open `http://localhost:4200`. The Angular dev server proxies `/api` calls to Spring Boot.

## GitHub deployment

GitHub Pages hosts the Angular frontend through the included GitHub Actions workflow. GitHub does not run Java/Spring Boot applications, so the `backend` folder is published to the repository but must be deployed to a Java-capable host (for example Render, Railway, or Azure App Service). Once deployed, update the frontend API URL to point to that backend service.
