# Stage 1: Build the Angular application
FROM node:alpine AS build-stage

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json first
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the Angular project files
COPY . .

# Build the application for production
RUN npm run build --prod

# Stage 2: Serve the Angular app with a simple Node.js server
FROM node:alpine

# Set the working directory
WORKDIR /app

# Install a lightweight HTTP server (like serve or http-server)
RUN npm install -g serve

# Copy the built Angular app from the previous stage
COPY --from=build-stage /app/dist/my_app/browser /app

# Expose port 8080 for the HTTP server
EXPOSE 80

# Serve the Angular app and handle client-side routing
CMD ["serve", "-s", ".", "-l", "80"]
