
FROM node:16-alpine AS frontend-builder
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY frontend ./
RUN npm run build


# Stage 2: Финальный образ на базе Caddy
FROM caddy:2
COPY caddy/Caddyfile /etc/caddy/Caddyfile
COPY --from=frontend-builder /app/dist /usr/share/caddy
EXPOSE 80
CMD ["caddy", "run", "--config", "/etc/caddy/Caddyfile"]
