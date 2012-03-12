 ;-
 ; Copyright (c) 2012 Ryan Kelker and individual contributors.
 ; ( https://github.com/runexec/Roov )
 ; All rights reserved.
 ;
 ; Redistribution and use in source and binary forms, with or without
 ; modification, are permitted provided that the following conditions
 ; are met:
 ; 1. Redistributions of source code must retain the above copyright
 ; notice, this list of conditions and the following disclaimer
 ; in this position and unchanged.
 ; 2. Redistributions in binary form must reproduce the above copyright
 ; notice, this list of conditions and the following disclaimer in the
 ; documentation and/or other materials provided with the distribution.
 ; 3. The name of the author may not be used to endorse or promote products
 ; derived from this software withough specific prior written permission
 ;
 ; THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 ; IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 ; OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 ; IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 ; INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 ; NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 ; DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 ; THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 ; (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 ; THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ;
 ;
(ns Roov.core
  (:use [Roov.http :only (httpGET)]
        [clojure.java.io :only (as-url)]
        [clojure.string :only (trim)])
  (:import (java.net URLEncoder)))
 
(def api_key 
  (try
    (trim (slurp "api.key"))
    (catch Exception e
      (println
        "Could not read Rotten Tomatoes API Key: " e)
      (System/exit 1)); catch
    ); try
); api_key

; Theater Movie JSONs
(def jsonInTheaters "/movies/in_theaters.json")
(def jsonBoxOffice "/movies/box_office.json")
(def jsonOpeningMovies "/movies/opening.json")
(def jsonUpcomingMovies "/movies/upcoming.json")
; DVD Movie JSONs
(def jsonTopRentals "/dvds/top_rentals.json")
(def jsonCurrentRelease "/dvds/current_releases.json")
(def jsonNewDVDs "/dvds/new_releases.json")
(def jsonUpcomingDVDs "/dvds/upcoming.json")

(def urlList "http://api.rottentomatoes.com/api/public/v1.0/lists")
(def urlMovie "http://api.rottentomatoes.com/api/public/v1.0/movies")
(def urlMovieAlias "http://api.rottentomatoes.com/api/public/v1.0/movie_alias.json")
(def urlMovieSearch "http://api.rottentomatoes.com/api/public/v1.0/movies.json")
  
(defn movieSearch
  [query page_limit page]
    (httpGET  (str urlMovieSearch
                   "?apikey=" api_key
                    "&q=" (. URLEncoder encode query)
                    "&page_limit=" page_limit
                    "&page=" page)
             );httpGet 
);movieSearch

(defn movieAlias
  [imdb_id]
    (httpGET (str urlMovieAlias
                  "?id=" imdb_id
                  "&type=imdb"
                  "&apikey=" api_key))
);movieAlias

(defn movieSimilar
  ([movie_id]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/similar.jon?apikey=" api_key)))
  ([movie_id page_limit]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/similar.jon?apikey=" api_key
                  "&limit=" page_limit)))
);movieSimilar

(defn movieReviews
  ([movie_id]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/reviews.json?apikey=" api_key)))
  ([movie_id review_type]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/reviews.json?apikey=" api_key
                  "&review_type=" review_type)))
  ([movie_id review_type page_limit]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/reviews.json?apikey=" api_key
                  "&review_type=" review_type
                  "&page_limit=" page_limit)))
  ([movie_id review_type page_limit page]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/reviews.json?apikey=" api_key
                  "&review_type=" review_type
                  "&page_limit=" page_limit
                  "&page=" page)))
  ([movie_id review_type page_limit page country]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/reviews.json?apikey=" api_key
                  "&review_type=" review_type
                  "&page_limit=" page_limit
                  "&page=" page
                  "&country=" country)))
);movieReviews

(defn movieClips
  [movie_id]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/clips.json?apikey=" api_key))
);movieClips


(defn castInfo
  [movie_id]
    (httpGET (str urlMovie
                  "/" movie_id
                  "/cast.json?apikey=" api_key))
);castInfo

(defn movieInfo
  [movie_id]
  (httpGET (str urlMovie
                "/" movie_id
                ".json?apikey=" api_key))
);movieInfo

(defn upcomingDVDs
  ([page_limit]
    (httpGET (str urlList
                  jsonUpcomingDVDs
                  "?page_limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit page]
    (httpGET (str urlList
                  jsonUpcomingDVDs
                  "?page_limit=" page_limit
                  "&page=" page
                  "&apikey=" api_key)))
  ([page_limit page country]
    (httpGET (str urlList
                  jsonUpcomingDVDs
                  "?page_limit=" page_limit
                  "&page=" page
                  "&country=" country
                  "&apikey=" api_key)))
);upcomingDVDs

(defn newDVDs
  ([page_limit]
    (httpGET (str urlList
                  jsonNewDVDs
                  "?page_limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit page]
    (httpGET (str urlList
                  jsonNewDVDs
                  "?page_limit=" page_limit
                  "&page=" page
                  "&apikey=" api_key)))
  ([page_limit page country]
    (httpGET (str urlList
                  jsonNewDVDs
                  "?page_limit=" page_limit
                  "&page=" page
                  "&country=" country
                  "&apikey=" api_key)))
);newDVDs

(defn currentRelease
  "Current release dvds."
  ([page_limit]
    (httpGET (str urlList
                  jsonCurrentRelease
                  "?page_limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit page]
    (httpGET (str urlList
                  jsonCurrentRelease
                  "?page_limit=" page_limit
                  "&page=" page
                  "&apikey=" api_key)))
  ([page_limit page country]
    (httpGET (str urlList
                  jsonCurrentRelease
                  "?page_limit=" page_limit
                  "&page=" page
                  "&country=" country
                  "&apikey=" api_key)))
);currentRelease

(defn topRentals
  ([page_limit]
    (httpGET (str urlList
                  jsonTopRentals
                  "?limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit country]
    (httpGET (str urlList
                  jsonTopRentals
                  "?limit=" page_limit
                  "&country=" country
                  "&apikey=" api_key)))
);topRentals

(defn upcomingMovies
  ([page_limit]
    (httpGET (str urlList
                  jsonUpcomingMovies
                  "?page_limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit page]
    (httpGET (str urlList
                  jsonUpcomingMovies
                  "?page_limit=" page_limit
                  "&page=" page
                  "&apikey=" api_key)))
  ([page_limit page country]
    (httpGET (str urlList
                  jsonUpcomingMovies
                  "?page_limit-" page_limit
                  "&page=" page
                  "&country=" country
                  "&apikey=" api_key)))
);upcomingMovies

(defn openingMovies
  ([page_limit]
    (httpGET (str urlList
                  jsonOpeningMovies
                  "?limit=" page_limit)))
  ([page_limit country]
    (httpGET (str urlList
                  jsonOpeningMovies
                  "?limit=" page_limit
                  "&country=" country)))
);openingMovies

(defn boxOffice
  "Top grossing movies"
  ([page_limit]
    (httpGET (str urlList
                  jsonBoxOffice
                  "?limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit country]
    (httpGET (str urlList
                  jsonBoxOffice
                  "?limit=" page_limit
                  "&country=" country
                  "&apikey=" api_key)))
);boxOffice

(defn inTheaters
  "Latest movies in theaters"
  ([page_limit]
    (httpGET (str urlList
                  jsonInTheaters
                  "?page_limit=" page_limit
                  "&apikey=" api_key)))
  ([page_limit page]
    (httpGET (str urlList
                  jsonInTheaters
                  "?page_limit=" page_limit
                  "&page=" page
                  "&apikey=" api_key)))
  ([page_limit page country]
    (httpGET (str urlList
                  jsonInTheaters
                  "?page_limit=" page_limit
                  "&page=" page
                  "&country=" country
                  "&apikey=" api_key)))
);inTheaters

