# Talk-To-Past-Self 🧠

A GenAI-powered application that allows users to interact with their past thoughts and conversations using **RAG (Retrieval-Augmented Generation)**.

The application stores previous conversations, converts them into embeddings, retrieves semantically relevant memories using vector similarity search, and provides those memories as context to Gemini to generate personalized responses.

## 🚀 Tech Stack

* **Java**
* **Spring Boot**
* **LangChain4j**
* **Google Gemini**
* **PostgreSQL**
* **pgvector**
* **Spring Data JPA**
* **JdbcTemplate**
* **RAG**
* **Vector Embeddings**

## 🏗️ Architecture

```text
User
 │
 ▼
Spring Boot REST API
 │
 ▼
Chat Service
 │
 ├──────────────► Gemini
 │                 │
 │                 ▼
 │             AI Response
 │
 ▼
Generate Embedding
 │
 ▼
PostgreSQL + pgvector
 │
 ▼
Vector Similarity Search
 │
 ▼
Relevant Past Conversations
 │
 ▼
Context + Current Question
 │
 ▼
Gemini
 │
 ▼
Final Response
```

## 🔄 How It Works

1. User sends a question.
2. The question is converted into an embedding using Gemini.
3. PostgreSQL with pgvector performs a semantic similarity search.
4. Relevant past conversations are retrieved.
5. The retrieved conversations are converted into context.
6. The context and current question are sent to Gemini.
7. Gemini generates a context-aware response.
8. The new conversation and its embedding are stored in PostgreSQL.

## 🧠 RAG Pipeline

```text
Question
   ↓
Embedding
   ↓
Vector Search
   ↓
Relevant Memories
   ↓
Context Augmentation
   ↓
Gemini
   ↓
Generated Answer
```

## 🗄️ Database

Each conversation stores:

| Field          | Description                               |
| -------------- | ----------------------------------------- |
| `id`           | Conversation ID                           |
| `userId`       | User identifier                           |
| `userQuestion` | User's question                           |
| `aiAnswer`     | AI-generated answer                       |
| `createdAt`    | Time of the conversation                  |
| `embedding`    | Vector representation of the conversation |

The `embedding` column uses PostgreSQL's **pgvector** extension.

## ⚙️ Configuration

The Gemini API key is provided through an environment variable:

```properties
gemini.api.key=${GEMINI_API_KEY}
```

Set the environment variable before running the application.

**Never commit your API key to GitHub.**

## ▶️ Running the Project

Clone the repository:

```bash
git clone https://github.com/swapnanilC/Talk-To-Past-Self.git
cd Talk-To-Past-Self
```

Run the application using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## 🎯 Current Features

* Chat with Gemini
* Store conversations in PostgreSQL
* Generate Gemini embeddings
* Store embeddings using pgvector
* Semantic similarity search
* Retrieve relevant past conversations
* Augment Gemini prompts with retrieved context
* Context-aware responses using RAG

## 🔮 Future Improvements

* Long-term memory extraction
* Important-memory detection
* Memory categorization
* Date-based memory retrieval
* User authentication
* Conversation history API
* Improved similarity scoring
* Memory management and deletion
* Better prompt management
* Production-ready deployment

## 📌 Project Goal

The goal of **Talk-To-Past-Self** is to explore how Generative AI, embeddings, vector databases, and RAG can be combined to build an AI assistant that understands and reflects on a user's past experiences.

---

Built with ☕ Java, Spring Boot, Gemini and RAG.

