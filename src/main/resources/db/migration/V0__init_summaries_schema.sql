CREATE TABLE summaries (
  id           SERIAL PRIMARY KEY,
  title        TEXT,
  reading_time TEXT,
  highlights   JSONB,
  sources      JSONB,
  content      TEXT,
  created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  updated_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);