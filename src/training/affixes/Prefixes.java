package training.affixes;

import java.util.LinkedHashMap;

public class Prefixes {

	private final String vowel = "[aeiou].*";
	private final String consonant = "[^aeiou-].*";

	private final String assimilation_ng_to_n = "[dlrst].*";
	private final String assimilation_ng_to_m = "[bp].*";
	LinkedHashMap<String, String> pattern = null;

	public Prefixes() {
		pattern = new LinkedHashMap<String, String>();

		pattern.put("^pakikipag-" + vowel, "pakikipag-");
		pattern.put("^pakikipag" + consonant, "pakikipag");

		pattern.put("^pagpapaka", "pagpapaka");
		pattern.put("^nakipagsa", "nakipagsa");
		pattern.put("^nakapagpa", "nakapagpa");

		pattern.put("^maipapang-" + vowel, "maipapang-");
		pattern.put("^maipapan" + assimilation_ng_to_n, "maipapan");
		pattern.put("^maipapam" + assimilation_ng_to_m, "maipapam");
		pattern.put("^maipapang" + consonant, "maipapang");

		pattern.put("^magkasing-" + vowel, "magkasing-");
		pattern.put("^magkasin" + assimilation_ng_to_n, "magkasin");
		pattern.put("^magkasim" + assimilation_ng_to_m, "magkasim");
		pattern.put("^magkasing" + consonant, "magkasing");

		pattern.put("^nasipang-" + vowel, "nasipang-");
		pattern.put("^nasipan" + assimilation_ng_to_n, "nasipan");
		pattern.put("^nasipam" + assimilation_ng_to_m, "nasipam");
		pattern.put("^nasipang" + consonant, "nasipang");

		pattern.put("^nakapang-" + vowel, "nakapang-");
		pattern.put("^nakapan" + assimilation_ng_to_n, "nakapan");
		pattern.put("^nakapam" + assimilation_ng_to_m, "nakapam");
		pattern.put("^nakapang" + consonant, "nakapang");

		pattern.put("^makapang-" + vowel, "makapang-");
		pattern.put("^makapan" + assimilation_ng_to_n, "makapan");
		pattern.put("^makapam" + assimilation_ng_to_m, "makapam");
		pattern.put("^makapang" + consonant, "makapang");

		pattern.put("^maipagpa.*", "maipagpa");

		pattern.put("^magsipag-" + vowel, "magsipag-");
		pattern.put("^magsipag" + consonant, "magsipag");

		pattern.put("^magkanda", "magkanda");
		pattern.put("^ikapagpa", "ikapagpa");

		pattern.put("^tagapag-" + vowel, "tagapag-");
		pattern.put("^tagapag" + consonant, "tagapag");

		pattern.put("^pagkaka.*", "pagkaka");
		pattern.put("^pagpapa.*", "pagpapa");
		pattern.put("^papagka.*", "papagka");

		pattern.put("^nakipag-" + vowel, "nakipag-");
		pattern.put("^nakipag" + consonant, "nakipag");

		pattern.put("^nakapag-" + vowel, "nakapag-");
		pattern.put("^nakapag" + consonant, "nakapag");

		pattern.put("^nagpaka.*", "nagpaka");

		pattern.put("^mapapag-" + vowel, "mapapag-");
		pattern.put("^mapapag" + consonant, "mapapag");

		pattern.put("^mangaka.*", "mangaka");
		pattern.put("^mapagpa.*", "mapagpa");

		pattern.put("^makipag-" + vowel, "makipag-");
		pattern.put("^makipag" + consonant, "makipag");

		pattern.put("^makapag-" + vowel, "makapag-");
		pattern.put("^makapag" + consonant, "makapag");

		pattern.put("^maipang-" + vowel, "maipang-");
		pattern.put("^maipan" + assimilation_ng_to_n, "maipan");
		pattern.put("^maipam" + assimilation_ng_to_m, "maipam");
		pattern.put("^maipang" + consonant, "maipang");

		pattern.put("^magpaka.*", "magpaka");

		pattern.put("^magkang-" + vowel, "magkang-");
		pattern.put("^magkan" + assimilation_ng_to_n, "magkan");
		pattern.put("^magkam" + assimilation_ng_to_m, "magkam");
		pattern.put("^magkang" + consonant, "magkang");

		pattern.put("^ipapang-" + vowel, "ipapang-");
		pattern.put("^ipapan" + assimilation_ng_to_n, "ipapan");
		pattern.put("^ipapam" + assimilation_ng_to_m, "ipapam");
		pattern.put("^ipapang" + consonant, "ipapang");

		pattern.put("^ikapang-" + vowel, "ikapang-");
		pattern.put("^ikapan" + assimilation_ng_to_n, "ikapan");
		pattern.put("^ikapam" + assimilation_ng_to_m, "ikapam");
		pattern.put("^ikapang" + consonant, "ikapang");

		pattern.put("^papang-" + vowel, "papang-");
		pattern.put("^papan" + assimilation_ng_to_n, "papan");
		pattern.put("^papam" + assimilation_ng_to_m, "papam");
		pattern.put("^papang" + consonant, "papang");

		pattern.put("^nakaka.*", "nakaka");
		pattern.put("^napaka.*", "napaka");
		pattern.put("^pakiki.*", "pakiki");

		pattern.put("^naipag-" + vowel, "naipag-");
		pattern.put("^naipag" + consonant, "naipag");

		pattern.put("^naging-" + vowel, "naging-");
		pattern.put("^nagin" + assimilation_ng_to_n, "nagin");
		pattern.put("^nagim" + assimilation_ng_to_m, "nagim");
		pattern.put("^naging" + consonant, "naging");

		pattern.put("^mapang-" + vowel, "mapang-");
		pattern.put("^mapan" + assimilation_ng_to_n, "mapan");
		pattern.put("^mapam" + assimilation_ng_to_m, "mapam");
		pattern.put("^mapang" + consonant, "mapang");

		pattern.put("^kasing-" + vowel, "kasing-");
		pattern.put("^kasin" + assimilation_ng_to_n, "kasin");
		pattern.put("^kasim" + assimilation_ng_to_m, "kasim");
		pattern.put("^kasing" + consonant, "kasing");

		pattern.put("^maging-" + vowel, "maging-");
		pattern.put("^magin" + assimilation_ng_to_n, "magin");
		pattern.put("^magim" + assimilation_ng_to_m, "magim");
		pattern.put("^maging" + consonant, "maging");

		pattern.put("^maipag-" + vowel, "maipag-");
		pattern.put("^maipag" + consonant, "maipag");

		pattern.put("^mangag-" + vowel, "mangag-");
		pattern.put("^mangag" + consonant, "mangag");

		pattern.put("^ipagka.*", "ipagka");

		pattern.put("^ikapag-" + vowel, "ikapag-");
		pattern.put("^ikapag" + consonant, "ikapag");

		pattern.put("^papag-" + vowel, "papag-");
		pattern.put("^papag" + consonant, "papag");

		pattern.put("^nagpa.*", "nagpa");
		pattern.put("^nagsa.*", "nagsa");
		pattern.put("^naipa.*", "naipa");
		pattern.put("^pagka.*", "pagka");
		pattern.put("^pagpa.*", "pagpa");

		pattern.put("^mapag-" + vowel, "mapag-");
		pattern.put("^mapag" + consonant, "mapag");

		pattern.put("^magka.*", "magka");
		pattern.put("^magpa.*", "magpa");
		pattern.put("^magsa.*", "magsa");
		pattern.put("^magsi.*", "magsi");
		pattern.put("^maipa.*", "maipa");
		pattern.put("^manga.*", "manga");

		pattern.put("^ipang-" + vowel, "ipang-");
		pattern.put("^ipan" + assimilation_ng_to_n, "ipan");
		pattern.put("^ipam" + assimilation_ng_to_m, "ipam");
		pattern.put("^ipang" + consonant, "ipang");

		pattern.put("^ikina.*", "ikina");
		pattern.put("^ipaki.*", "ipaki");
		pattern.put("^taga-.*", "taga-");
		pattern.put("^tala.*", "tala");

		pattern.put("^sang-" + vowel, "sang-");
		pattern.put("^san" + assimilation_ng_to_n, "san");
		pattern.put("^sam" + assimilation_ng_to_m, "sam");
		pattern.put("^sang" + consonant, "sang");

		pattern.put("^sing-" + vowel, "sing-");
		pattern.put("^sin" + assimilation_ng_to_n, "sin");
		pattern.put("^sim" + assimilation_ng_to_m, "sim");
		pattern.put("^sing" + consonant, "sing");

		pattern.put("^pina.*", "pina");
		pattern.put("^puma.*", "puma");

		pattern.put("^pang-" + vowel, "pang-");
		pattern.put("^pan" + assimilation_ng_to_n, "pan");
		pattern.put("^pam" + assimilation_ng_to_m, "pam");
		pattern.put("^pang" + consonant, "pang");

		pattern.put("^napa.*", "napa");
		pattern.put("^paka.*", "paka");
		pattern.put("^paki.*", "paki");
		pattern.put("^pala.*", "pala");

		pattern.put("^nang-" + vowel, "nang-");
		pattern.put("^nan" + assimilation_ng_to_n, "nan");
		pattern.put("^nam" + assimilation_ng_to_m, "nam");
		pattern.put("^nang" + consonant, "nang");

		pattern.put("^mapa.*", "mapa");
		pattern.put("^naka.*", "naka");
		pattern.put("^naki.*", "naki");

		pattern.put("^mang-" + vowel, "mang-");
		pattern.put("^man" + assimilation_ng_to_n, "man");
		pattern.put("^mam" + assimilation_ng_to_m, "mam");
		pattern.put("^mang" + consonant, "mang");

		pattern.put("^maka.*", "maka");
		pattern.put("^maki.*", "maki");
		pattern.put("^mala.*", "mala");

		pattern.put("^ipag-" + vowel, "ipag-");
		pattern.put("^ipag" + consonant, "ipag");

		pattern.put("^tig-" + vowel, "tig-");
		pattern.put("^tig" + consonant, "tig");

		pattern.put("^tag-" + vowel, "tag-");
		pattern.put("^tag" + consonant, "tag");

		pattern.put("^pag-" + vowel, "pag-");
		pattern.put("^pag" + consonant, "pag");

		pattern.put("^nai.*", "nai");

		pattern.put("^may-" + vowel, "may-");
		pattern.put("^may" + consonant, "may");

		pattern.put("^nag-" + vowel, "nag-");
		pattern.put("^nag" + consonant, "nag");

		pattern.put("^mai.*", "mai");

		pattern.put("^mag-" + vowel, "mag-");
		pattern.put("^mag" + consonant, "mag");

		pattern.put("^ika.*", "ika");
		pattern.put("^ipa.*", "ipa");
		pattern.put("^isa.*", "isa");

		pattern.put("^di-.*", "di-");
	}

	public LinkedHashMap<String, String> getPrefixes() {
		return pattern;
	}
}
