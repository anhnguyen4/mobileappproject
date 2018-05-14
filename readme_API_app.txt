
There are 2 important things with this App:
	1. Allow the app access INTERNET in Manifest.xml
	2. App only can access INTERNET through AsyncTask<>

Others:
1. Running:
	- We enter an input, then the App will translate from EN to Vi
		+ If cannot translate: it will show in result. (in English)
		+ If it can: the result also in the result. (in Vietnamese)
	- JSON format of result:
		{"outputs":[
			{"output":"con voi",
			"stats":{"elapsed_time":182,"nb_characters":3,"nb_tokens":1,"nb_tus":1,"nb_tus_failed":0}}]}
	