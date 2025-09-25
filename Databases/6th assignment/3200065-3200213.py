import psycopg2
from matplotlib import pyplot as plt

# Update connection string information
host = "server3200065-3200213.postgres.database.azure.com"
dbname = "postgres"
user = "examiner@server3200065-3200213"
password = "3200065_3200213"
sslmode = "require"

# Construct connection string
conn_string = "host={0} user={1} dbname={2} password={3} sslmode={4}".format(host, user, dbname, password, sslmode)
conn = psycopg2.connect(conn_string)
print("Connection established")

cursor = conn.cursor()

#QUESTION 1
cursor.execute("SELECT EXTRACT(Year from \"movie\".release_date) AS releaseYear, COUNT(EXTRACT(YEAR FROM \"movie\".release_date)) as num_Movies FROM \"movie\" WHERE EXTRACT(YEAR FROM \"movie\".release_date) IS NOT NULL GROUP BY EXTRACT(YEAR FROM \"movie\".release_date) ORDER BY EXTRACT(YEAR FROM \"movie\".release_date) ASC;")
years = []
num_movies = []
data = cursor.fetchall()
i = 0
for d in data:
    years.append(d[0])
    num_movies.append(d[1])
    i+=1
conn.commit()
plt.plot(years,num_movies)
plt.title("ΕΡΩΤΗΜΑ 1")
plt.xlabel("RELEASE YEAR")
plt.ylabel("NUMBER OF MOVIES")
plt.show()

#QUESTION 2
cursor.execute("SELECT \"genre\".name AS genre, COUNT(\"movie_genres\".genre_id) as num_Movies FROM \"genre\" INNER JOIN \"movie_genres\" ON \"movie_genres\".genre_id=\"genre\".id GROUP BY \"genre\".name;")
genres = []
num_movies = []
data = cursor.fetchall()
i = 0
for d in data:
    genres.append(d[0])
    num_movies.append(d[1])
    i+=1
conn.commit()
plt.plot(genres,num_movies)
plt.title("ΕΡΩΤΗΜΑ 2")
plt.xlabel("GENRES")
plt.ylabel("NUMBER OF MOVIES")
plt.show()

#QUESTION 3
cursor.execute("SELECT \"genre\".name AS genre, EXTRACT(Year from \"movie\".release_date) AS releaseYear, COUNT(*) AS num_Movies FROM \"genre\" INNER JOIN \"movie_genres\" ON \"movie_genres\".genre_id=\"genre\".id INNER JOIN \"movie\" ON \"movie_genres\".movie_id=\"movie\".id WHERE EXTRACT(YEAR FROM \"movie\".release_date) IS NOT NULL GROUP BY \"genre\".name, EXTRACT(YEAR FROM \"movie\".release_date) ORDER BY \"genre\".name;")
genres = []
years = []
num_movies = []
data = cursor.fetchall()
i = 0
for d in data:
    genres.append(d[0])
    years.append(d[1])
    num_movies.append(d[2])
    i+=1
conn.commit()
plt.plot(genres,years,label = "years")
plt.plot(genres,num_movies,label = "num_movies")
plt.title("ΕΡΩΤΗΜΑ 3")
plt.xlabel("GENRES")
plt.ylabel("YEARS/MOVIES")
plt.show()

#QUESTION 4
cursor.execute("SELECT EXTRACT(YEAR FROM \"movie\".release_date) as releaseYear,MAX(\"movie\".budget) as maxBudget FROM \"movie\" WHERE EXTRACT(YEAR FROM \"movie\".release_date) IS NOT NULL GROUP BY EXTRACT(YEAR FROM \"movie\".release_date) ORDER BY EXTRACT(YEAR FROM \"movie\".release_date) ASC;")
years = []
budget = []
data = cursor.fetchall()
i = 0
for d in data:
    years.append(d[0])
    budget.append(d[1])
    i+=1
conn.commit()
plt.plot(years,budget)
plt.title("ΕΡΩΤΗΜΑ 4")
plt.xlabel("YEARS")
plt.ylabel("BUDGET")
plt.show()

#QUESTION 5
cursor.execute("SELECT EXTRACT(YEAR FROM \"movie\".release_date) as releaseYear,SUM(\"movie\".budget) as budget FROM \"movie\" INNER JOIN \"movie_cast\"  ON \"movie\".id=\"movie_cast\".movie_id WHERE \"movie_cast\".person_id=500 GROUP BY EXTRACT(YEAR FROM \"movie\".release_date) ORDER BY EXTRACT(YEAR FROM \"movie\".release_date) ASC;")
years = []
revenue = []
data = cursor.fetchall()
i = 0
for d in data:
    years.append(d[0])
    revenue.append(d[1])
    i+=1
conn.commit()
plt.plot(years,revenue)
plt.title("ΕΡΩΤΗΜΑ 5")
plt.xlabel("YEARS")
plt.ylabel("REVENUE")
plt.show()

#QUESTION 6
cursor.execute("SELECT \"ratings\".user_id as user,AVG(\"ratings\".rating) as avgRating FROM \"ratings\" GROUP BY \"ratings\".user_id ORDER BY \"ratings\".user_id ASC;")
ratings = []
users = []
data = cursor.fetchall()
i = 0
for d in data:
    ratings.append(d[0])
    users.append(d[1])
    i+=1
conn.commit()
plt.scatter(ratings,users)
plt.title("ΕΡΩΤΗΜΑ 6")
plt.xlabel("AVERAGE RATING")
plt.ylabel("USER_ID")
plt.show()

#QUESTION 7
cursor.execute("SELECT \"ratings\".user_id as user,COUNT(\"ratings\".rating) as avgRating FROM \"ratings\" GROUP BY \"ratings\".user_id ORDER BY \"ratings\".user_id ASC;")
ratings = []
users = []
data = cursor.fetchall()
i = 0
for d in data:
    ratings.append(d[0])
    users.append(d[1])
    i+=1
conn.commit()
plt.scatter(ratings,users)
plt.title("ΕΡΩΤΗΜΑ 7")
plt.xlabel("RATINGS")
plt.ylabel("USER_ID")
plt.show()

#QUESTION 8
cursor.execute("SELECT COUNT(\"ratings\".rating) as ratings, AVG(\"ratings\".rating) as avgRating FROM \"ratings\" GROUP BY \"ratings\".user_id ORDER BY \"ratings\".user_id ASC;")
x = []
y = []
data = cursor.fetchall()
i = 0
for d in data:
    x.append(d[0])
    y.append(d[1])
    i+=1
conn.commit()
plt.scatter(x,y)
plt.title("ΕΡΩΤΗΜΑ 8")
plt.xlabel("NUMBER OF RATINGS")
plt.ylabel("AVERAGE RATINGS")
plt.show()

#QUESTION 9
cursor.execute("SELECT \"genre\".name as genre,AVG(\"ratings\".rating) as avgRating FROM \"genre\" INNER JOIN \"movie_genres\" ON \"movie_genres\".genre_id=\"genre\".id INNER JOIN \"ratings\" ON \"ratings\".movie_id=\"movie_genres\".movie_id GROUP BY \"genre\".name;")
genres = []
ratings = []
data = cursor.fetchall()
i = 0
for d in data:
    genres.append(d[0])
    ratings.append(d[1])
    i+=1
conn.commit()
plt.plot(genres,ratings)
plt.title("ΕΡΩΤΗΜΑ 9")
plt.xlabel("GENRES")
plt.ylabel("AVERAGE RATING")
plt.show()

cursor.close()
conn.close()