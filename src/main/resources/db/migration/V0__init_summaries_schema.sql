CREATE TABLE summaries (
  id           UUID PRIMARY KEY,
  title        VARCHAR(255) NOT NULL,
  description  TEXT NOT NULL,
  reading_time VARCHAR(50) NOT NULL,
  highlights   JSONB,
  sources      JSONB,
  content      TEXT,
  created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  updated_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);