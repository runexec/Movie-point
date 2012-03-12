# Movie-point

A movie finder via relational ranking to seperate the good from the bad.<br />

Place your <b>Rotten Tomatoes API Key</b> in the <b>api.key</b> file and get started. =>
<code>$lein run</code>

<h3>How it works.</h3>
Example:<br />
Movie: {:title Project Nim, :rating 1033}

<p />
<dl>
	<dt>Project Nim</dt>
<dd>
	<dl>
	<dt>Rating = </dt>
	<dd>Sum of <br />
	[critic_rating] +
	<br />
	[viewer_rating] +
	<br />
	[related_videos_critic_rating] + (No more than 40 related movies)
	<br />
	[related_videos_viewer_rating] + (No more than 40 related movies)
	</dd>
	</dl>
</dd>

<h3>Why it works.</h3>
<b>Good movies are not like, similar, and or related to bad movies.</b>
<br />
A good movie will probably be like, similar, and or related to other good movies.<br />
Calculating the rank of up to 40 relationships per movie can help weed out the bad from the good.

<b>Sources</b>
<ul>
<li>Rotten Tomatoes New DVDs</li>
<li>Rotten Tomatoes Box Office</li>
<li>Rotten Tomatoes In Theaters</li>
<ul>

<h3> First Prediction List </h3>
<div>
=== Sorted Best to Worse ===<br />
{:title Project Nim, :rating 1033}<br />
{:title The Girl with the Dragon Tattoo, :rating 960}<br />
{:title The Descendants, :rating 922}<br />
{:title Mission: Impossible Ghost Protocol, :rating 917}<br />
{:title Tinker Tailor Soldier Spy, :rating 913}<br />
{:title Sherlock Holmes: A Game of Shadows, :rating 858}<br />
{:title Lady and the Tramp, :rating 854}<br />
{:title Star Wars: Episode I - The Phantom Menace, :rating 844}<br />
{:title Puss in Boots, :rating 844}<br />
{:title The Adventures of Tintin, :rating 819}<br />
{:title Anonymous, :rating 812}<br />
{:title London Boulevard, :rating 787}<br />
{:title Elite Squad: The Enemy Within, :rating 763}<br />
{:title Happy Feet Two, :rating 749}<br />
{:title The Artist, :rating 727}<br />
{:title Footloose, :rating 726}<br />
{:title The Thing, :rating 719}<br />
{:title The Rum Diary, :rating 706}<br />
{:title Twilight Saga: Breaking Dawn Part 1, :rating 668}<br />
{:title Young Adult, :rating 659}<br />
{:title My Week with Marilyn, :rating 654}<br />
{:title Hugo, :rating 649}<br />
{:title Immortals, :rating 548}<br />
{:title Fireflies in the Garden, :rating 535}<br />
{:title In Time, :rating 534}<br />
{:title The Three Musketeers, :rating 516}<br />
{:title The Human Centipede II (Full Sequence), :rating 516}<br />
{:title The Interrupters, :rating 375}<br />
{:title Ghost Rider: Spirit of Vengeance, :rating 335}<br />
{:title Alvin and the Chipmunks: Chipwrecked, :rating 322}<br />
{:title Take Shelter, :rating 320}<br />
{:title Martha Marcy May Marlene, :rating 313}<br />
{:title A Very Harold & Kumar Christmas, :rating 297}<br />
{:title Melancholia, :rating 241}<br />
{:title The Rebound, :rating 196}<br />
{:title A Separation, :rating 193}<br />
{:title The Secret World of Arrietty, :rating 182}<br />
{:title In Darkness, :rating 175}<br />
{:title Drive, :rating 171}<br />
{:title We Need to Talk About Kevin, :rating 162}<br />
{:title Jack and Jill, :rating 161}<br />
{:title Chronicle, :rating 160}<br />
{:title Miss Bala, :rating 154}<br />
{:title War Horse, :rating 150}<br />
{:title The Grey, :rating 148}<br />
{:title We Bought a Zoo, :rating 144}<br />
{:title Salmon Fishing in the Yemen, :rating 136}<br />
{:title Big Miracle, :rating 135}<br />
{:title The Lorax, :rating 130}<br />
{:title The Woman in Black, :rating 128}<br />
{:title Safe House, :rating 124}<br />
{:title Tiny Furniture, :rating 123}<br />
{:title Haywire, :rating 122}<br />
{:title Tower Heist, :rating 122}<br />
{:title John Carter, :rating 121}<br />
{:title Friends With Kids, :rating 120}<br />
{:title Wanderlust, :rating 116}<br />
{:title Tyler Perry's Good Deeds, :rating 114}<br />
{:title Act of Valor, :rating 114}<br />
{:title Contraband, :rating 111}<br />
{:title Extremely Loud & Incredibly Close, :rating 109}<br />
{:title The Iron Lady, :rating 107}<br />
{:title Joyful Noise, :rating 104}<br />
{:title Red Tails, :rating 101}<br />
{:title Journey 2: The Mysterious Island, :rating 99}<br />
{:title Project X, :rating 98}<br />
{:title This Means War, :rating 96}<br />
{:title Man on a Ledge, :rating 92}<br />
{:title Silent House, :rating 92}<br />
{:title The Vow, :rating 91}<br />
{:title Johnny English Reborn, :rating 85}<br />
{:title A Thousand Words, :rating 62}<br />
{:title Gone, :rating 60}<br />
{:title Dream House, :rating 43}<br />
{:title The Son of No One, :rating 40}<br />
</div>

## License

Copyright © 2012 Ryan Kelker
Distributed under the Eclipse Public License, the same as Clojure.
