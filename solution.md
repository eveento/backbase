App load all data from csv to db (flyway). Using  mapstruct data from db are mapper to DTOs and returned if needed.
Api run query to external api and retrive data. These data and cached in memcache. It is automaticly run everyday at 18:00 or if there is no data in cache, then retrive and cache again.
User can rate movie by title, and get avarage of rate for selected movie.