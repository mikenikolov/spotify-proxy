# 🔀 Spotify Proxy 🔀
Spotify proxy service to search for **artists** with their top tracks,
genres and save them in the cache for the next request.

## 🎯 Features
- Find artists by their Spotify ID
- Find artists by their name
- Show all artists from cache

## 🔨 Technologies used in the project
- Java 11
- Spring Boot / Web / Security / Data
- Maven 3.8.1
- H2 Database
- Hibernate
- Lombok
- Apache HttpClient
- Jackson JSON
- OAuth 2.0
- Official Spotify API

## 🌐 Endpoints
|   Method   | URL                      | Description                   |
|:----------:|--------------------------|:------------------------------|
|   `GET`    | `/v1/login`              | `Login with Spotify`          |
| `POST/GET` | `/logout`                | `Logout`                      |
|   `GET`    | `/v1/search`             | `Show all artists from cache` |
|   `GET`    | `/v1/search/id/{id}`     | `Find artist by Spotify ID`   |
|   `GET`    | `/v1/search/name/{name}` | `Find artist by their name`   |

## ❓ How to setup

**1**. Install Java 11+, Maven ([Help Link](https://mkyong.com/maven/how-to-install-maven-in-windows/)) and Git

**2**. Clone the newest version of the project

**3**. Create an application in `Spotify for Developers` ([Help Link](https://developer.spotify.com/documentation/general/guides/authorization/app-settings/))

**4**. In `Spotify for Developers` set `Redirect URIs`

You can find this URI in `src/main/resources/application.properties`,

Redirect URI: `spotify.security.redirect.uri = redirect-uri`

**5**. Copy **Client ID** and **Client Secret** from Dashboard into `src/main/resources/application.properties`

For Client ID: `spotify.security.client.id = your-spotify-id`

For Client Secret: `spotify.security.client.secret = your-spotify-secret`

**OR**

Creating system variables using the command line, Windows example:

`setx SPOTIFY.CLIENT.ID your-spotify-client-id`

`setx SPOTIFY.CLIENT.SECRET your-spotify-client-secret`

After setting system variables **❗ don't forget to restart your command line ❗** and only then go
to the next step

**6**. Navigate to the root directory of the project and run it using the console with the following command
(or just open this project in the IDE and run the `main()` method)

`mvn spring-boot:run`

## ⁉ How to use

**1️⃣: Authenticate**

Here we use **OAuth2** to authenticate to our service
because in order to make a request to the Spotify API, we need to be their user.

If you are not logged in yet and want to find an artist you have not cached
you will see the following message
![image](https://user-images.githubusercontent.com/101512791/198290623-44ae326f-7875-4f5b-ab7a-de75a71acf7a.png)

So to find artists that are not yet in the cache, you need to authenticate through your
Spotify account
following `/v1/login` and then you will be redirected to official
Spotify login form.
![image](https://user-images.githubusercontent.com/101512791/198287998-774d65a1-e2be-4892-bec2-c77f382e5331.png)

**2️⃣: Make a request**

So now we can make a request.
Let's find an artist named `FINNEAS`

![image](https://user-images.githubusercontent.com/101512791/198291015-6e8bf10c-5e7d-4768-993d-9683120f73b0.png)

Let's take a look at the console logs
![image](https://user-images.githubusercontent.com/101512791/198291427-433f6428-b7bd-4516-95e3-c8a7227af680.png)

We just found the artist and saved it in our cache (database), let's do the same query again

Nothing much has happened for the user of this service, the result is the same,
BUT as you can see at the console logs, this artist has now been loaded from the cache.
We didn't even hit the Spotify API :)
![image](https://user-images.githubusercontent.com/101512791/198291792-2c24860f-6fa9-4505-90f6-62301ee72d4e.png)

Also, we can make a request by Spotify ID
![image](https://user-images.githubusercontent.com/101512791/198295350-bf1686ec-a743-43cc-b6b4-e8c2237422c3.png)

❗ If the artist does not exist, we will receive the following message
![image](https://user-images.githubusercontent.com/101512791/198300641-e8367e6e-9123-4d4a-853b-955d8c5768fd.png)

❗ Please note that you can get an artist from the cache only by entering its full and
correct name, otherwise a request to the spotify server will occur, but if it has
been saved before, it will not be added to the cache.

Example: `/v1/search/name/finne` (should be `finneas`)

![image](https://user-images.githubusercontent.com/101512791/198301849-1267a1e4-7ba5-43a9-bd5d-7e148d26c16a.png)
![image](https://user-images.githubusercontent.com/101512791/198301633-0e6ded11-b443-4e33-885a-347820a04439.png)

The artist was found, but we don't store it as it is already in our cache

**3️⃣: Show all cached artists**

Even unauthenticated user can see cached artists with their genres and tracks
by running the same request or following the link `/v1/search` to see all cached artists
![image](https://user-images.githubusercontent.com/101512791/198295938-c6c70656-f5e7-412a-abcc-8c53d85e25d7.png)

The application supports **pagination**, by default, it's only 3 artists on the first page,
but it can be configured in

`src/main/resources/application.properties` -> `pageable.size = size-number`

Let's go to the second page by `/v1/search?page=2`
![image](https://user-images.githubusercontent.com/101512791/198296833-e4377c49-a92a-4472-90ea-806b31574587.png)

We can see the next page with artists.

## 🙏 Acknowledgements
I would like to thank my classmates **Vitaliy Vybornyi**, **Dmytro Vovk** and **Denis Kuchma** for
the great team work. I really enjoyed this work process 💖
