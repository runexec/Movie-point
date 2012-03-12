 ;-
 ; Copyright (c) 2012 Ryan Kelker and individual contributors.
 ; ( https://github.com/runexec/Movie-point )
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
(ns moviepoint.core
  (:require [clj-json.core :as json]
             [Roov.core :as RT :only (movieInfo 
                                       movieSimilar
                                       newDVDs
                                       inTheaters
                                       boxOffice)])
  (:use [clojure.java.io :only (reader)]
        [clojure.string :only (join split)])
  ); ns

(def PAGE_LIMIT 35)

(defn rt_detail_rankings
  " returns ranking hash-map of movie.
  [ :rt_id :imdb_id :title :rating ]
  SHOULD BE USED ONLY FOR PARSING MOVIE INFO REQUEST"
  [ json_code_str ]
  (let 
      [ m (json/parse-string json_code_str)
      title (get m "title")
      rt_id (get m "id")
      imdb_id (get (get m "alternate_ids") "imdb")
      ratings (get m "ratings")
      critics_rating (get ratings "critics_score")
      audience_rating (get ratings "audience_score")
      rating_totals (+ critics_rating audience_rating)
      movie_object (hash-map :rt_id rt_id,
                             :imdb_id imdb_id,
                             :title title,
                             :rating rating_totals)]
    ; return movie_object
    movie_object
    ); let
); rt_detail_rankings

(defn rt_search_rankings 
  " returns ranking array of hash-map.
  [ :rt_id :imdb_id :title :rating ]
  SHOULD BE USED ONLY FOR THE PARSING OF SEARCH REQUEST"
  [ json_code_str ]
  (let
    [ movies (get (json/parse-string json_code_str)
                  "movies")
    ranking (java.util.ArrayList.) ] 
    
    (comment
       Keys for rotten tomatoe movies
       ( abridged_cast
         posters
         release_dates
         alternate_ids
         synopsis ratings
         critics_consensus
         title
         runtime
         mpaa_rating
         links
         id
         year )
    ); comment

    (doseq [ m movies ]
     (let
       [title (get m "title")
        rt_id (get m "id")
        imdb_id (get (get m "alternate_ids") "imdb")
        ratings (get m "ratings")
        critics_rating (get ratings "critics_score")
        audience_rating (get ratings "audience_score")
        rating_totals (+ critics_rating audience_rating)
        movie_object (hash-map :rt_id rt_id,
                               :imdb_id imdb_id,
                               :title title,
                               :rating rating_totals)]
     
       (. ranking add movie_object)); let
      ); doseq
    
    ; Returning ranking array
    (sort-by :rating ranking)  
  ); let
); rt_rankings

(defn rt_relational_rank
  [ rotten_tomatoe_id ]
  " returns int of related_score/rank.
  related_score = sum of related movies rating"
  (let [ final_rank (java.util.ArrayList.) ] 
    (doseq [ movie (rt_search_rankings
             (RT/movieSimilar rotten_tomatoe_id)) ]
          (. final_rank add (get movie :rating))
      ); doseq

    ; return final_rank
    (reduce + final_rank)
  ); let
); rt_relational_rank


(defn -main [& args]
  (let
    [ ranked_movies (java.util.ArrayList.) 
     movie_lists [(rt_search_rankings
                    (RT/newDVDs PAGE_LIMIT))
                  (rt_search_rankings
                    (RT/inTheaters PAGE_LIMIT))
                  (rt_search_rankings
                    (RT/boxOffice PAGE_LIMIT))]]
 
    (doseq [ movies movie_lists ]
      (doseq [movie movies ]
        (def final_rank 
          (+ (rt_relational_rank 
               (get movie :rt_id))
             (get movie :rating)); +
          ); final_rank

        (def display_name (str final_rank
                            " " (get movie :title)
                            " " (get movie :imdb_id)
                            " " (get movie :rt_id)))
        (Thread/sleep 3000)
        (println "Adding => " display_name)
        (. ranked_movies add (hash-map
                               :rating final_rank,
                              :title (get movie :title)))
        ); doseq
      ); doseq

    (println "=== Sorted Best to Worse ===")
    ; Sort by rating
    (doseq [ m (reverse
                 (sort-by :rating 
                          (distinct ranked_movies)))]
      (println m)); doseq
   ); let
  ); -main
