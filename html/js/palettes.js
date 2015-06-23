palettes = {};

palettes.dem =
{
	"type": "palette",

	"allowedValues":
	{
		"ncolors": 256,
		"red": [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,45,50,60,60,70,70,80,80,79,79,78,78,77,77,76,76,75,75,74,74,73,73,72,72,71,71,70,70,69,68,67,66,65,64,63,62,61,48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,33,45,60,78,95,110,122,157,168,180,197,222,240,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,243,231,219,210,202,194,186,186,186,186,182,182,182,182,182,186,186,186,186,178,170,162,154,150,142,134,125,121,121,121,121,121,138,142,150,158,170,178,194,206,223,235,243,243,243,247,247,247,247,247,247,251,251,251,251,251,251,255,255,255],
		"green": [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,4,6,8,10,12,14,16,18,20,21,22,23,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72,74,76,78,80,82,84,86,88,90,94,96,105,111,113,115,117,118,119,120,121,123,124,125,126,127,128,129,130,131,132,134,135,136,137,138,139,140,143,150,160,180,200,225,12,32,57,81,109,113,117,125,130,132,134,136,138,142,146,154,162,168,174,182,194,206,215,222,227,239,251,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,252,248,243,240,235,231,228,225,221,206,194,185,176,166,155,144,133,122,111,109,105,97,93,93,89,85,93,97,105,109,117,121,130,134,146,150,154,162,154,146,142,134,125,117,113,105,97,85,77,69,65,85,97,105,117,138,150,170,190,210,231,243,239,235,231,227,223,219,215,210,210,206,202,198,194,190,186,182,178],
		"blue": [0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72,74,76,78,80,82,84,86,88,100,102,104,106,108,110,112,114,116,118,120,122,124,126,128,129,130,131,132,133,135,137,139,141,143,147,149,151,153,156,160,162,164,166,168,170,172,174,176,178,191,193,194,195,196,197,198,199,201,203,204,205,206,207,208,210,212,214,216,218,220,222,224,226,228,230,232,234,236,238,240,242,242,242,243,243,243,243,243,244,244,244,244,244,245,245,245,245,245,249,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,28,45,57,73,101,113,142,170,198,215,243,243,243,247,247,247,247,247,251,251,251,251,251,251,251,255,255,255]	
	},
	"nilValues": "PENDING!"
}
/*
palettes.gray =
{
	"type": "palette",

	"allowedValues":
	{
		"ncolors": 256,
		"red": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255],
		"green": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255],
		"blue": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255]	
	},
	"nilValues": "PENDING!"
}
*/
/*
palettes.rainbow =
{
	"type": "palette",

	"allowedValues":
	{
		"ncolors": 256,
		"red": [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,7,11,15,19,23,27,31,35,39,43,47,51,55,59,63,67,71,75,79,83,87,91,95,99,103,107,111,115,119,123,127,131,135,139,143,147,151,155,159,163,167,171,175,179,183,187,191,195,199,203,207,211,215,219,223,227,231,235,239,243,247,251,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255],
		"green": [0,3,7,11,15,19,23,27,31,35,39,43,47,51,55,59,63,67,71,75,79,83,87,91,95,99,103,107,111,115,119,123,127,131,135,139,143,147,151,155,159,163,167,171,175,179,183,187,191,195,199,203,207,211,215,219,223,227,231,235,239,243,247,251,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,251,247,243,239,235,231,227,223,219,215,211,207,203,199,195,191,187,183,179,175,171,167,163,159,155,151,147,143,139,135,131,127,123,119,115,111,107,103,99,95,91,87,83,79,75,71,67,63,59,55,51,47,43,39,35,31,27,23,19,15,11,7,3,0],
		"blue": [255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,251,247,243,239,235,231,227,223,219,215,211,207,203,199,195,191,187,183,179,175,171,167,163,159,155,151,147,143,139,135,131,127,123,119,115,111,107,103,99,95,91,87,83,79,75,71,67,63,59,55,51,47,43,39,35,31,27,23,19,15,11,7,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]	
	},
	"nilValues": "PENDING!"
}
*/
palettes.linearL =
{
	"type": "palette",

	"allowedValues":
	{
		"ncolors": 256,
		"red": [4,10,13,16,18,21,22,24,26,27,28,30,31,32,33,35,36,37,38,39,40,41,42,43,44,46,46,46,45,45,45,45,45,45,45,45,45,44,44,45,45,44,44,44,44,44,44,44,44,43,42,40,38,36,34,33,32,31,30,30,29,29,28,28,27,28,28,27,27,27,27,28,27,27,27,27,25,22,21,20,17,16,15,14,10,10,9,8,7,6,4,4,4,3,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,20,29,35,40,45,48,52,55,59,61,64,66,68,71,73,76,78,79,81,83,85,87,92,99,106,114,119,125,130,135,140,145,149,153,157,161,165,169,172,176,180,184,186,190,193,197,200,201,203,206,207,209,212,213,215,218,219,221,223,226,227,229,231,232,235,237,238,240,243,243,245,248,248,248,247,247,247,248,247,247,247,247,247,247,247,248,247,247,247,247,248,248,247,248,248,248,248,249,251,252,253,254,255],
		"green": [4,3,4,5,5,6,7,8,8,9,10,11,12,12,13,14,14,15,15,16,17,17,18,19,19,20,20,21,21,21,22,23,23,24,24,25,25,26,27,27,28,29,29,30,31,31,32,33,34,34,35,38,40,42,44,46,47,49,50,51,52,53,55,56,57,58,59,60,61,62,63,64,65,66,68,69,71,73,74,76,78,79,81,82,84,85,87,88,89,91,92,94,95,96,98,99,100,101,103,104,105,107,108,110,111,112,114,115,116,118,119,120,121,123,124,125,127,128,129,131,132,133,134,136,137,138,139,141,142,143,145,146,147,149,150,151,153,154,155,157,158,159,160,162,163,164,165,167,168,169,170,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,193,193,193,194,194,194,195,195,195,196,196,197,197,197,198,198,199,199,199,200,201,201,201,202,201,202,202,203,203,203,204,205,205,206,207,207,207,208,209,209,210,211,211,212,212,213,214,214,215,216,218,219,220,222,223,224,226,227,229,230,231,232,234,235,236,238,239,240,242,243,244,246,247,248,249,251,252,253,254,255],
		"blue": [4,8,11,14,16,18,19,21,22,24,25,26,27,28,29,31,32,32,33,34,35,36,38,38,39,41,45,50,55,60,64,67,71,75,77,81,84,87,90,92,95,98,100,103,106,109,110,113,116,118,121,120,119,120,120,120,120,121,122,123,123,125,125,126,127,128,129,129,131,132,133,134,135,136,137,138,136,134,133,131,129,128,126,125,123,122,120,119,118,117,115,114,114,112,111,110,109,108,107,106,105,104,101,100,99,98,96,95,93,92,90,89,88,86,85,83,82,80,79,77,75,73,72,70,68,66,65,64,63,61,60,60,58,57,56,55,53,52,51,50,48,47,45,44,42,41,39,36,34,31,23,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24,51,65,77,87,95,103,109,116,121,127,132,138,143,147,151,155,159,164,168,172,175,179,183,186,189,193,195,198,201,204,206,209,211,214,216,218,220,224,225,229,231,232,235,237,239,241,244,246,248,249,251,252,253,254,255]	
	},
	"nilValues": "PENDING!"
}