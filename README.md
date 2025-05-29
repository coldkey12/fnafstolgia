# Chat with Golden Freddy (NFAC2025 TOP 4%)

A simple Java Spring Boot web application where you can chat with Golden Freddy from the FNAF.

Live demo: https://fnf-413057889128.us-central1.run.app (CURRENTLY DOWN)

---

## Project Overview
- Inspired by the Five Nights at Freddy’s (FNAF) series.
- Mini-game where you chat with Golden Freddy.
- Focused on storytelling and atmosphere.

---

## How to Run Locally

**Requirements:**
- Java 17+
- Maven 3.8+

**Steps:**
1. Clone the repo:
   ```bash
   git clone https://github.com/coldkey12/fnafstolgia.git
   cd fnafstolgia
   ```
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access it at:
   ```
   http://localhost:8080
   ```

(Optional) If using Docker:
```bash
docker build -t golden-freddy-chat .
docker run -p 8080:8080 golden-freddy-chat
```

---

## Design and Development
- Simple MVC structure (Model-View-Controller).
- Lightweight HTML/CSS frontend.
- Deployment with Docker

---

## Unique Approaches
- Parsed text into objects with regex from chatgpt response (Just like when developing an AI Agent)
- Minimalist backend, no heavy frontend frameworks.
- Focus on nostalgic fnaf atmosphere over complexity.
- ## Easter eggs. (Make sure to send Golden Freddy atleast 6 messages!)

---

## Development Trade-offs
- No advanced user authentication or session management.
- Basic mobile responsiveness only.
- Optimized for small to traffic, not large-scale use.

---

## Known Issues
- No input validation for very long messages.
- Search API doesn't display pictures as intended due to links given by the API being invalid.
- Coldstart due to free hosting.

---

## Why This Tech Stack
- **Java Spring Boot**: Stable, powerful, great cloud support + great for server side.
- **Maven**: Standard for Java dependency management.
- **Google Cloud Run**: Easy deployment, auto-scaling without infrastructure management.

---
