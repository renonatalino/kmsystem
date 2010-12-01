package com.app.kmsystem.util;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;

/**
 * Provides miscellaneous Form, String and Stream utility methods.
 *
 * @author Malcolm Edgar
 */
public class StringUtils {

    /** Hexadecimal characters for MD5 encoding. */
    private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Character used to separate username and password in persistent cookies.
     * 0x13 == "Device Control 3" non-printing ASCII char. Unlikely to appear
     * in a username
     */
    private static final char DELIMITER = 0x13;

    /*
     * "Tweakable" parameters for the cookie encoding. NOTE: changing these
     *and recompiling this class will essentially invalidate old cookies.
     */
    private final static char ENCODE_CHAR_OFFSET1 = 'C';
    private final static char ENCODE_CHAR_OFFSET2 = 'i';

    /**
     * The array of escaped HTML character values, indexed on char value.
     * <p/>
     * HTML entities values were derived from Jakarta Commons Lang
     * <tt>org.apache.commons.lang.Entities</tt> class.
     */
    private static final String[] HTML_ENTITIES = new String[9999];

    static {
        HTML_ENTITIES[34] = "&quot;"; // " - double-quote
        HTML_ENTITIES[38] = "&amp;"; // & - ampersand
        HTML_ENTITIES[60] = "&lt;"; // < - less-than
        HTML_ENTITIES[62] = "&gt;"; // > - greater-than
        HTML_ENTITIES[160] = "&nbsp;"; // non-breaking space
        HTML_ENTITIES[161] = "&iexcl;"; // inverted exclamation mark
        HTML_ENTITIES[162] = "&cent;";  // cent sign
        HTML_ENTITIES[163] = "&pound;"; // pound sign
        HTML_ENTITIES[164] = "&curren;"; // currency sign
        HTML_ENTITIES[165] = "&yen;";  // yen sign = yuan sign
        HTML_ENTITIES[166] = "&brvbar;"; // broken bar = broken vertical bar
        HTML_ENTITIES[167] = "&sect;"; // section sign
        HTML_ENTITIES[168] = "&uml;"; // diaeresis = spacing diaeresis
        HTML_ENTITIES[169] = "&copy;"; // � - copyright sign
        HTML_ENTITIES[170] = "&ordf;"; // feminine ordinal indicator
        HTML_ENTITIES[171] = "&laquo;"; // left-pointing double angle quotation mark = left pointing guillemet
        HTML_ENTITIES[172] = "&not;";   //not sign
        HTML_ENTITIES[173] = "&shy;";   //soft hyphen = discretionary hyphen
        HTML_ENTITIES[174] = "&reg;";   // � - registered trademark sign
        HTML_ENTITIES[175] = "&macr;";   //macron = spacing macron = overline = APL overbar
        HTML_ENTITIES[176] = "&deg;";   //degree sign
        HTML_ENTITIES[177] = "&plusmn;";   //plus-minus sign = plus-or-minus sign
        HTML_ENTITIES[178] = "&sup2;";   //superscript two = superscript digit two = squared
        HTML_ENTITIES[179] = "&sup3;";   //superscript three = superscript digit three = cubed
        HTML_ENTITIES[180] = "&acute;";   //acute accent = spacing acute
        HTML_ENTITIES[181] = "&micro;";   //micro sign
        HTML_ENTITIES[182] = "&para;";   //pilcrow sign = paragraph sign
        HTML_ENTITIES[183] = "&middot;";   //middle dot = Georgian comma = Greek middle dot
        HTML_ENTITIES[184] = "&cedil;";   //cedilla = spacing cedilla
        HTML_ENTITIES[185] = "&sup1;";   //superscript one = superscript digit one
        HTML_ENTITIES[186] = "&ordm;";   //masculine ordinal indicator
        HTML_ENTITIES[187] = "&raquo;";   //right-pointing double angle quotation mark = right pointing guillemet
        HTML_ENTITIES[188] = "&frac14;";   //vulgar fraction one quarter = fraction one quarter
        HTML_ENTITIES[189] = "&frac12;";   //vulgar fraction one half = fraction one half
        HTML_ENTITIES[190] = "&frac34;";   //vulgar fraction three quarters = fraction three quarters
        HTML_ENTITIES[191] = "&iquest;";   //inverted question mark = turned question mark
        HTML_ENTITIES[192] = "&Agrave;";   // � - uppercase A, grave accent
        HTML_ENTITIES[193] = "&Aacute;";   // � - uppercase A, acute accent
        HTML_ENTITIES[194] = "&Acirc;";   // � - uppercase A, circumflex accent
        HTML_ENTITIES[195] = "&Atilde;";   // � - uppercase A, tilde
        HTML_ENTITIES[196] = "&Auml;";   // � - uppercase A, umlaut
        HTML_ENTITIES[197] = "&Aring;";   // � - uppercase A, ring
        HTML_ENTITIES[198] = "&AElig;";   // � - uppercase AE
        HTML_ENTITIES[199] = "&Ccedil;";   // � - uppercase C, cedilla
        HTML_ENTITIES[200] = "&Egrave;";   // � - uppercase E, grave accent
        HTML_ENTITIES[201] = "&Eacute;";   // � - uppercase E, acute accent
        HTML_ENTITIES[202] = "&Ecirc;";   // � - uppercase E, circumflex accent
        HTML_ENTITIES[203] = "&Euml;";   // � - uppercase E, umlaut
        HTML_ENTITIES[204] = "&Igrave;";   // � - uppercase I, grave accent
        HTML_ENTITIES[205] = "&Iacute;";   // � - uppercase I, acute accent
        HTML_ENTITIES[206] = "&Icirc;";   // � - uppercase I, circumflex accent
        HTML_ENTITIES[207] = "&Iuml;";   // � - uppercase I, umlaut
        HTML_ENTITIES[208] = "&ETH;";   // � - uppercase Eth, Icelandic
        HTML_ENTITIES[209] = "&Ntilde;";   // � - uppercase N, tilde
        HTML_ENTITIES[210] = "&Ograve;";   // � - uppercase O, grave accent
        HTML_ENTITIES[211] = "&Oacute;";   // � - uppercase O, acute accent
        HTML_ENTITIES[212] = "&Ocirc;";   // � - uppercase O, circumflex accent
        HTML_ENTITIES[213] = "&Otilde;";   // � - uppercase O, tilde
        HTML_ENTITIES[214] = "&Ouml;";   // � - uppercase O, umlaut
        HTML_ENTITIES[215] = "&times;";   //multiplication sign
        HTML_ENTITIES[216] = "&Oslash;";   // � - uppercase O, slash
        HTML_ENTITIES[217] = "&Ugrave;";   // � - uppercase U, grave accent
        HTML_ENTITIES[218] = "&Uacute;";   // � - uppercase U, acute accent
        HTML_ENTITIES[219] = "&Ucirc;";   // � - uppercase U, circumflex accent
        HTML_ENTITIES[220] = "&Uuml;";   // � - uppercase U, umlaut
        HTML_ENTITIES[221] = "&Yacute;";   // � - uppercase Y, acute accent
        HTML_ENTITIES[222] = "&THORN;";   // � - uppercase THORN, Icelandic
        HTML_ENTITIES[223] = "&szlig;";   // � - lowercase sharps, German
        HTML_ENTITIES[224] = "&agrave;";   // � - lowercase a, grave accent
        HTML_ENTITIES[225] = "&aacute;";   // � - lowercase a, acute accent
        HTML_ENTITIES[226] = "&acirc;";   // � - lowercase a, circumflex accent
        HTML_ENTITIES[227] = "&atilde;";   // � - lowercase a, tilde
        HTML_ENTITIES[228] = "&auml;";   // � - lowercase a, umlaut
        HTML_ENTITIES[229] = "&aring;";   // � - lowercase a, ring
        HTML_ENTITIES[230] = "&aelig;";   // � - lowercase ae
        HTML_ENTITIES[231] = "&ccedil;";   // � - lowercase c, cedilla
        HTML_ENTITIES[232] = "&egrave;";   // � - lowercase e, grave accent
        HTML_ENTITIES[233] = "&eacute;";   // � - lowercase e, acute accent
        HTML_ENTITIES[234] = "&ecirc;";   // � - lowercase e, circumflex accent
        HTML_ENTITIES[235] = "&euml;";   // � - lowercase e, umlaut
        HTML_ENTITIES[236] = "&igrave;";   // � - lowercase i, grave accent
        HTML_ENTITIES[237] = "&iacute;";   // � - lowercase i, acute accent
        HTML_ENTITIES[238] = "&icirc;";   // � - lowercase i, circumflex accent
        HTML_ENTITIES[239] = "&iuml;";   // � - lowercase i, umlaut
        HTML_ENTITIES[240] = "&eth;";   // � - lowercase eth, Icelandic
        HTML_ENTITIES[241] = "&ntilde;";   // � - lowercase n, tilde
        HTML_ENTITIES[242] = "&ograve;";   // � - lowercase o, grave accent
        HTML_ENTITIES[243] = "&oacute;";   // � - lowercase o, acute accent
        HTML_ENTITIES[244] = "&ocirc;";   // � - lowercase o, circumflex accent
        HTML_ENTITIES[245] = "&otilde;";   // � - lowercase o, tilde
        HTML_ENTITIES[246] = "&ouml;";   // � - lowercase o, umlaut
        HTML_ENTITIES[247] = "&divide;";   // division sign
        HTML_ENTITIES[248] = "&oslash;";   // � - lowercase o, slash
        HTML_ENTITIES[249] = "&ugrave;";   // � - lowercase u, grave accent
        HTML_ENTITIES[250] = "&uacute;";   // � - lowercase u, acute accent
        HTML_ENTITIES[251] = "&ucirc;";   // � - lowercase u, circumflex accent
        HTML_ENTITIES[252] = "&uuml;";   // � - lowercase u, umlaut
        HTML_ENTITIES[253] = "&yacute;";   // � - lowercase y, acute accent
        HTML_ENTITIES[254] = "&thorn;";   // � - lowercase thorn, Icelandic
        HTML_ENTITIES[255] = "&yuml;";   // � - lowercase y, umlaut
        // http://www.w3.org/TR/REC-html40/sgml/entities.html
        // <!-- Latin Extended-B -->
        HTML_ENTITIES[402] = "&fnof;";   //latin small f with hook = function= florin, U+0192 ISOtech -->
        // <!-- Greek -->
        HTML_ENTITIES[913] = "&Alpha;";   //greek capital letter alpha, U+0391 -->
        HTML_ENTITIES[914] = "&Beta;";   //greek capital letter beta, U+0392 -->
        HTML_ENTITIES[915] = "&Gamma;";   //greek capital letter gamma,U+0393 ISOgrk3 -->
        HTML_ENTITIES[916] = "&Delta;";   //greek capital letter delta,U+0394 ISOgrk3 -->
        HTML_ENTITIES[917] = "&Epsilon;";   //greek capital letter epsilon, U+0395 -->
        HTML_ENTITIES[918] = "&Zeta;";   //greek capital letter zeta, U+0396 -->
        HTML_ENTITIES[919] = "&Eta;";   //greek capital letter eta, U+0397 -->
        HTML_ENTITIES[920] = "&Theta;";   //greek capital letter theta,U+0398 ISOgrk3 -->
        HTML_ENTITIES[921] = "&Iota;";   //greek capital letter iota, U+0399 -->
        HTML_ENTITIES[922] = "&Kappa;";   //greek capital letter kappa, U+039A -->
        HTML_ENTITIES[923] = "&Lambda;";   //greek capital letter lambda,U+039B ISOgrk3 -->
        HTML_ENTITIES[924] = "&Mu;";   //greek capital letter mu, U+039C -->
        HTML_ENTITIES[925] = "&Nu;";   //greek capital letter nu, U+039D -->
        HTML_ENTITIES[926] = "&Xi;";   //greek capital letter xi, U+039E ISOgrk3 -->
        HTML_ENTITIES[927] = "&Omicron;";   //greek capital letter omicron, U+039F -->
        HTML_ENTITIES[928] = "&Pi;";   //greek capital letter pi, U+03A0 ISOgrk3 -->
        HTML_ENTITIES[929] = "&Rho;";   //greek capital letter rho, U+03A1 -->
        // <!-- there is no Sigmaf, and no U+03A2 character either -->
        HTML_ENTITIES[931] = "&Sigma;";   //greek capital letter sigma,U+03A3 ISOgrk3 -->
        HTML_ENTITIES[932] = "&Tau;";   //greek capital letter tau, U+03A4 -->
        HTML_ENTITIES[933] = "&Upsilon;";   //greek capital letter upsilon,U+03A5 ISOgrk3 -->
        HTML_ENTITIES[934] = "&Phi;";   //greek capital letter phi,U+03A6 ISOgrk3 -->
        HTML_ENTITIES[935] = "&Chi;";   //greek capital letter chi, U+03A7 -->
        HTML_ENTITIES[936] = "&Psi;";   //greek capital letter psi,U+03A8 ISOgrk3 -->
        HTML_ENTITIES[937] = "&Omega;";   //greek capital letter omega,U+03A9 ISOgrk3 -->
        HTML_ENTITIES[945] = "&alpha;";   //greek small letter alpha,U+03B1 ISOgrk3 -->
        HTML_ENTITIES[946] = "&beta;";   //greek small letter beta, U+03B2 ISOgrk3 -->
        HTML_ENTITIES[947] = "&gamma;";   //greek small letter gamma,U+03B3 ISOgrk3 -->
        HTML_ENTITIES[948] = "&delta;";   //greek small letter delta,U+03B4 ISOgrk3 -->
        HTML_ENTITIES[949] = "&epsilon;";   //greek small letter epsilon,U+03B5 ISOgrk3 -->
        HTML_ENTITIES[950] = "&zeta;";   //greek small letter zeta, U+03B6 ISOgrk3 -->
        HTML_ENTITIES[951] = "&eta;";   //greek small letter eta, U+03B7 ISOgrk3 -->
        HTML_ENTITIES[952] = "&theta;";   //greek small letter theta,U+03B8 ISOgrk3 -->
        HTML_ENTITIES[953] = "&iota;";   //greek small letter iota, U+03B9 ISOgrk3 -->
        HTML_ENTITIES[954] = "&kappa;";   //greek small letter kappa,U+03BA ISOgrk3 -->
        HTML_ENTITIES[955] = "&lambda;";   //greek small letter lambda,U+03BB ISOgrk3 -->
        HTML_ENTITIES[956] = "&mu;";   //greek small letter mu, U+03BC ISOgrk3 -->
        HTML_ENTITIES[957] = "&nu;";   //greek small letter nu, U+03BD ISOgrk3 -->
        HTML_ENTITIES[958] = "&xi;";   //greek small letter xi, U+03BE ISOgrk3 -->
        HTML_ENTITIES[959] = "&omicron;";   //greek small letter omicron, U+03BF NEW -->
        HTML_ENTITIES[960] = "&pi;";   //greek small letter pi, U+03C0 ISOgrk3 -->
        HTML_ENTITIES[961] = "&rho;";   //greek small letter rho, U+03C1 ISOgrk3 -->
        HTML_ENTITIES[962] = "&sigmaf;";   //greek small letter final sigma,U+03C2 ISOgrk3 -->
        HTML_ENTITIES[963] = "&sigma;";   //greek small letter sigma,U+03C3 ISOgrk3 -->
        HTML_ENTITIES[964] = "&tau;";   //greek small letter tau, U+03C4 ISOgrk3 -->
        HTML_ENTITIES[965] = "&upsilon;";   //greek small letter upsilon,U+03C5 ISOgrk3 -->
        HTML_ENTITIES[966] = "&phi;";   //greek small letter phi, U+03C6 ISOgrk3 -->
        HTML_ENTITIES[967] = "&chi;";   //greek small letter chi, U+03C7 ISOgrk3 -->
        HTML_ENTITIES[968] = "&psi;";   //greek small letter psi, U+03C8 ISOgrk3 -->
        HTML_ENTITIES[969] = "&omega;";   //greek small letter omega,U+03C9 ISOgrk3 -->
        HTML_ENTITIES[977] = "&thetasym;";   //greek small letter theta symbol,U+03D1 NEW -->
        HTML_ENTITIES[978] = "&upsih;";   //greek upsilon with hook symbol,U+03D2 NEW -->
        HTML_ENTITIES[982] = "&piv;";   //greek pi symbol, U+03D6 ISOgrk3 -->
        // <!-- General Punctuation -->
        HTML_ENTITIES[8226] = "&bull;";   //bullet = black small circle,U+2022 ISOpub  -->
        // <!-- bullet is NOT the same as bullet operator, U+2219 -->
        HTML_ENTITIES[8230] = "&hellip;";   //horizontal ellipsis = three dot leader,U+2026 ISOpub  -->
        HTML_ENTITIES[8242] = "&prime;";   //prime = minutes = feet, U+2032 ISOtech -->
        HTML_ENTITIES[8243] = "&Prime;";   //double prime = seconds = inches,U+2033 ISOtech -->
        HTML_ENTITIES[8254] = "&oline;";   //overline = spacing overscore,U+203E NEW -->
        HTML_ENTITIES[8260] = "&frasl;";   //fraction slash, U+2044 NEW -->
        // <!-- Letterlike Symbols -->
        HTML_ENTITIES[8472] = "&weierp;";   //script capital P = power set= Weierstrass p, U+2118 ISOamso -->
        HTML_ENTITIES[8465] = "&image;";   //blackletter capital I = imaginary part,U+2111 ISOamso -->
        HTML_ENTITIES[8476] = "&real;";   //blackletter capital R = real part symbol,U+211C ISOamso -->
        HTML_ENTITIES[8482] = "&trade;";   //trade mark sign, U+2122 ISOnum -->
        HTML_ENTITIES[8501] = "&alefsym;";   //alef symbol = first transfinite cardinal,U+2135 NEW -->
        // <!-- alef symbol is NOT the same as hebrew letter alef,U+05D0 although the same glyph could be used to depict both characters -->
        // <!-- Arrows -->
        HTML_ENTITIES[8592] = "&larr;";   //leftwards arrow, U+2190 ISOnum -->
        HTML_ENTITIES[8593] = "&uarr;";   //upwards arrow, U+2191 ISOnum-->
        HTML_ENTITIES[8594] = "&rarr;";   //rightwards arrow, U+2192 ISOnum -->
        HTML_ENTITIES[8595] = "&darr;";   //downwards arrow, U+2193 ISOnum -->
        HTML_ENTITIES[8596] = "&harr;";   //left right arrow, U+2194 ISOamsa -->
        HTML_ENTITIES[8629] = "&crarr;";   //downwards arrow with corner leftwards= carriage return, U+21B5 NEW -->
        HTML_ENTITIES[8656] = "&lArr;";   //leftwards double arrow, U+21D0 ISOtech -->
        // <!-- ISO 10646 does not say that lArr is the same as the 'is implied by' arrowbut also does not have any other character for that function. So ? lArr canbe used for 'is implied by' as ISOtech suggests -->
        HTML_ENTITIES[8657] = "&uArr;";   //upwards double arrow, U+21D1 ISOamsa -->
        HTML_ENTITIES[8658] = "&rArr;";   //rightwards double arrow,U+21D2 ISOtech -->
        // <!-- ISO 10646 does not say this is the 'implies' character but does not have another character with this function so ?rArr can be used for 'implies' as ISOtech suggests -->
        HTML_ENTITIES[8659] = "&dArr;";   //downwards double arrow, U+21D3 ISOamsa -->
        HTML_ENTITIES[8660] = "&hArr;";   //left right double arrow,U+21D4 ISOamsa -->
        // <!-- Mathematical Operators -->
        HTML_ENTITIES[8704] = "&forall;";   //for all, U+2200 ISOtech -->
        HTML_ENTITIES[8706] = "&part;";   //partial differential, U+2202 ISOtech  -->
        HTML_ENTITIES[8707] = "&exist;";   //there exists, U+2203 ISOtech -->
        HTML_ENTITIES[8709] = "&empty;";   //empty set = null set = diameter,U+2205 ISOamso -->
        HTML_ENTITIES[8711] = "&nabla;";   //nabla = backward difference,U+2207 ISOtech -->
        HTML_ENTITIES[8712] = "&isin;";   //element of, U+2208 ISOtech -->
        HTML_ENTITIES[8713] = "&notin;";   //not an element of, U+2209 ISOtech -->
        HTML_ENTITIES[8715] = "&ni;";   //contains as member, U+220B ISOtech -->
        // <!-- should there be a more memorable name than 'ni'? -->
        HTML_ENTITIES[8719] = "&prod;";   //n-ary product = product sign,U+220F ISOamsb -->
        // <!-- prod is NOT the same character as U+03A0 'greek capital letter pi' though the same glyph might be used for both -->
        HTML_ENTITIES[8721]  = "&sum;";   //n-ary summation, U+2211 ISOamsb -->
        // <!-- sum is NOT the same character as U+03A3 'greek capital letter sigma' though the same glyph might be used for both -->
        HTML_ENTITIES[8722]  = "&minus;";   //minus sign, U+2212 ISOtech -->
        HTML_ENTITIES[8727]  = "&lowast;";   //asterisk operator, U+2217 ISOtech -->
        HTML_ENTITIES[8730]  = "&radic;";   //square root = radical sign,U+221A ISOtech -->
        HTML_ENTITIES[8733]  = "&prop;";   //proportional to, U+221D ISOtech -->
        HTML_ENTITIES[8734]  = "&infin;";   //infinity, U+221E ISOtech -->
        HTML_ENTITIES[8736] = "&ang;";   //angle, U+2220 ISOamso -->
        HTML_ENTITIES[8743] = "&and;";   //logical and = wedge, U+2227 ISOtech -->
        HTML_ENTITIES[8744] = "&or;";   //logical or = vee, U+2228 ISOtech -->
        HTML_ENTITIES[8745] = "&cap;";   //intersection = cap, U+2229 ISOtech -->
        HTML_ENTITIES[8746] = "&cup;";   //union = cup, U+222A ISOtech -->
        HTML_ENTITIES[8747] = "&int;";   //integral, U+222B ISOtech -->
        HTML_ENTITIES[8756] = "&there4;";   //therefore, U+2234 ISOtech -->
        HTML_ENTITIES[8764] = "&sim;";   //tilde operator = varies with = similar to,U+223C ISOtech -->
        // <!-- tilde operator is NOT the same character as the tilde, U+007E,although the same glyph might be used to represent both  -->
        HTML_ENTITIES[8773] = "&cong;";   //approximately equal to, U+2245 ISOtech -->
        HTML_ENTITIES[8776] = "&asymp;";   //almost equal to = asymptotic to,U+2248 ISOamsr -->
        HTML_ENTITIES[8800] = "&ne;";   //not equal to, U+2260 ISOtech -->
        HTML_ENTITIES[8801] = "&equiv;";   //identical to, U+2261 ISOtech -->
        HTML_ENTITIES[8804] = "&le;";   //less-than or equal to, U+2264 ISOtech -->
        HTML_ENTITIES[8805] = "&ge;";   //greater-than or equal to,U+2265 ISOtech -->
        HTML_ENTITIES[8834] = "&sub;";   //subset of, U+2282 ISOtech -->
        HTML_ENTITIES[8835] = "&sup;";   //superset of, U+2283 ISOtech -->
        // <!-- note that nsup, 'not a superset of, U+2283' is not covered by the Symbol font encoding and is not included. Should it be, for symmetry?It is in ISOamsn  --> <!ENTITY nsub"; 8836   //not a subset of, U+2284 ISOamsn -->
        HTML_ENTITIES[8838] = "&sube;";   //subset of or equal to, U+2286 ISOtech -->
        HTML_ENTITIES[8839] = "&supe;";   //superset of or equal to,U+2287 ISOtech -->
        HTML_ENTITIES[8853] = "&oplus;";   //circled plus = direct sum,U+2295 ISOamsb -->
        HTML_ENTITIES[8855] = "&otimes;";   //circled times = vector product,U+2297 ISOamsb -->
        HTML_ENTITIES[8869] = "&perp;";   //up tack = orthogonal to = perpendicular,U+22A5 ISOtech -->
        HTML_ENTITIES[8901] = "&sdot;";   //dot operator, U+22C5 ISOamsb -->
        // <!-- dot operator is NOT the same character as U+00B7 middle dot -->
        // <!-- Miscellaneous Technical -->
        HTML_ENTITIES[8968] = "&lceil;";   //left ceiling = apl upstile,U+2308 ISOamsc  -->
        HTML_ENTITIES[8969] = "&rceil;";   //right ceiling, U+2309 ISOamsc  -->
        HTML_ENTITIES[8970] = "&lfloor;";   //left floor = apl downstile,U+230A ISOamsc  -->
        HTML_ENTITIES[8971] = "&rfloor;";   //right floor, U+230B ISOamsc  -->
        HTML_ENTITIES[9001] = "&lang;";   //left-pointing angle bracket = bra,U+2329 ISOtech -->
        // <!-- lang is NOT the same character as U+003C 'less than' or U+2039 'single left-pointing angle quotation mark' -->
        HTML_ENTITIES[9002]  = "&rang;";   //right-pointing angle bracket = ket,U+232A ISOtech -->
        // <!-- rang is NOT the same character as U+003E 'greater than' or U+203A 'single right-pointing angle quotation mark' -->
        // <!-- Geometric Shapes -->
        HTML_ENTITIES[9674] = "&loz;";   //lozenge, U+25CA ISOpub -->
        // <!-- Miscellaneous Symbols -->
        HTML_ENTITIES[9824] = "&spades;";   //black spade suit, U+2660 ISOpub -->
        // <!-- black here seems to mean filled as opposed to hollow -->
        HTML_ENTITIES[9827] = "&clubs;";   //black club suit = shamrock,U+2663 ISOpub -->
        HTML_ENTITIES[9829] = "&hearts;";   //black heart suit = valentine,U+2665 ISOpub -->
        HTML_ENTITIES[9830] = "&diams;";   //black diamond suit, U+2666 ISOpub -->
        // <!-- Latin Extended-A -->
        HTML_ENTITIES[338] = "&OElig;";   //  -- latin capital ligature OE,U+0152 ISOlat2 -->
        HTML_ENTITIES[339] = "&oelig;";   //  -- latin small ligature oe, U+0153 ISOlat2 -->
        // <!-- ligature is a misnomer, this is a separate character in some languages -->
        HTML_ENTITIES[352] = "&Scaron;";   //  -- latin capital letter S with caron,U+0160 ISOlat2 -->
        HTML_ENTITIES[353] = "&scaron;";   //  -- latin small letter s with caron,U+0161 ISOlat2 -->
        HTML_ENTITIES[376] = "&Yuml;";   //  -- latin capital letter Y with diaeresis,U+0178 ISOlat2 -->
        // <!-- Spacing Modifier Letters -->
        HTML_ENTITIES[710] = "&circ;";   //  -- modifier letter circumflex accent,U+02C6 ISOpub -->
        HTML_ENTITIES[732] = "&tilde;";   //small tilde, U+02DC ISOdia -->
        // <!-- General Punctuation -->
        HTML_ENTITIES[8194] = "&ensp;";   //en space, U+2002 ISOpub -->
        HTML_ENTITIES[8195] = "&emsp;";   //em space, U+2003 ISOpub -->
        HTML_ENTITIES[8201] = "&thinsp;";   //thin space, U+2009 ISOpub -->
        HTML_ENTITIES[8204] = "&zwnj;";   //zero width non-joiner,U+200C NEW RFC 2070 -->
        HTML_ENTITIES[8205] = "&zwj;";   //zero width joiner, U+200D NEW RFC 2070 -->
        HTML_ENTITIES[8206] = "&lrm;";   //left-to-right mark, U+200E NEW RFC 2070 -->
        HTML_ENTITIES[8207] = "&rlm;";   //right-to-left mark, U+200F NEW RFC 2070 -->
        HTML_ENTITIES[8211] = "&ndash;";   //en dash, U+2013 ISOpub -->
        HTML_ENTITIES[8212] = "&mdash;";   //em dash, U+2014 ISOpub -->
        HTML_ENTITIES[8216] = "&lsquo;";   //left single quotation mark,U+2018 ISOnum -->
        HTML_ENTITIES[8217] = "&rsquo;";   //right single quotation mark,U+2019 ISOnum -->
        HTML_ENTITIES[8218] = "&sbquo;";   //single low-9 quotation mark, U+201A NEW -->
        HTML_ENTITIES[8220] = "&ldquo;";   //left double quotation mark,U+201C ISOnum -->
        HTML_ENTITIES[8221] = "&rdquo;";   //right double quotation mark,U+201D ISOnum -->
        HTML_ENTITIES[8222] = "&bdquo;";   //double low-9 quotation mark, U+201E NEW -->
        HTML_ENTITIES[8224] = "&dagger;";   //dagger, U+2020 ISOpub -->
        HTML_ENTITIES[8225] = "&Dagger;";   //double dagger, U+2021 ISOpub -->
        HTML_ENTITIES[8240] = "&permil;";   //per mille sign, U+2030 ISOtech -->
        HTML_ENTITIES[8249] = "&lsaquo;";   //single left-pointing angle quotation mark,U+2039 ISO proposed -->
        // <!-- lsaquo is proposed but not yet ISO standardized -->
        HTML_ENTITIES[8250] = "&rsaquo;";   //single right-pointing angle quotation mark,U+203A ISO proposed -->
        // <!-- rsaquo is proposed but not yet ISO standardized -->
        HTML_ENTITIES[8364] = "&euro;";   //  -- euro sign, U+20AC NEW -->
    };

    // --------------------------------------------------------- Public Methods

    /**
     * Return a new XML Document for the given input stream.
     *
     * @param inputStream the input stream
     * @return new XML Document
     * @throws RuntimeException if a parsing error occurs
     */
    public static Document buildDocument(InputStream inputStream) {
        return buildDocument(inputStream, null);
    }

    /**
     * Return a new XML Document for the given input stream and XML entity
     * resolver.
     *
     * @param inputStream the input stream
     * @param entityResolver the XML entity resolver
     * @return new XML Document
     * @throws RuntimeException if a parsing error occurs
     */
    public static Document buildDocument(InputStream inputStream,
                                         EntityResolver entityResolver) {
         try {
             DocumentBuilderFactory factory =
                 DocumentBuilderFactory.newInstance();

             DocumentBuilder builder = factory.newDocumentBuilder();

             if (entityResolver != null) {
                 builder.setEntityResolver(entityResolver);
             }

             return builder.parse(inputStream);

         } catch (Exception ex) {
             throw new RuntimeException("Error parsing XML", ex);
         }
    }

    /**
     * Close the given input stream and ignore any exceptions thrown.
     *
     * @param stream the input stream to close.
     */
    public static void close(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                // Ignore.
            }
        }
    }

    /**
     * Close the given output stream and ignore any exceptions thrown.
     *
     * @param stream the output stream to close.
     */
    public static void close(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                // Ignore.
            }
        }
    }

    /**
     * Close the given reader and ignore any exceptions thrown.
     *
     * @param reader the reader to close.
     */
    public static void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }

    /**
     * Invalidate the specified cookie and delete it from the response object.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param request the servlet request
     * @param response the servlet response
     * @param cookieName The name of the cookie you want to delete
     * @param path of the path the cookie you want to delete
     */
    public static void invalidateCookie(HttpServletRequest request,
            HttpServletResponse response, String cookieName, String path) {

        setCookie(request, response, cookieName, null, 0, path);
    }

    /**
     * Invalidate the specified cookie and delete it from the response object. Deletes only cookies mapped
     * against the root "/" path. Otherwise use
     * {@link #invalidateCookie(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, String, String)}
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @see #invalidateCookie(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, String, String)
     *
     * @param request the servlet request
     * @param response the servlet response
     * @param cookieName The name of the cookie you want to delete.
     */
    public static void invalidateCookie(HttpServletRequest request,
            HttpServletResponse response, String cookieName) {

        invalidateCookie(request, response, cookieName, "/");
    }

    /**
     * Returns the specified Cookie object, or null if the cookie does not exist.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param request the servlet request
     * @param name the name of the cookie
     * @return the Cookie object if it exists, otherwise null
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();

        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }

        //Otherwise, we have to do a linear scan for the cookie.
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i];
            }
        }

        return null;
    }

    /**
     * Sets the given cookie values in the servlet response.
     * <p/>
     * This will also put the cookie in a list of cookies to send with this request's response
     * (so that in case of a redirect occurring down the chain, the first filter
     * will always try to set this cookie again)
     * <p/>
     * The cookie secure flag is set if the request is secure.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param request the servlet request
     * @param response the servlet response
     * @param name the cookie name
     * @param value the cookie value
     * @param maxAge the maximum age of the cookie in seconds. A negative
     * value will expire the cookie at the end of the session, while 0 will delete
     * the cookie.
     * @param path the cookie path
     * @return the Cookie object created and set in the response
     */
    public static Cookie setCookie(HttpServletRequest request, HttpServletResponse response,
            String name, String value, int maxAge, String path) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);

        return cookie;
    }

    /**
     * Returns the value of the specified cookie as a String. If the cookie
     * does not exist, the method returns null.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param request the servlet request
     * @param name the name of the cookie
     * @return the value of the cookie, or null if the cookie does not exist.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);

        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }


    /**
     * Builds a cookie string containing a username and password.
     * <p/>
     * Note: with open source this is not really secure, but it prevents users
     * from snooping the cookie file of others and by changing the XOR mask and
     * character offsets, you can easily tweak results.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param username the username
     * @param password the password
     * @param xorMask the XOR mask to encrypt the value with, must be same as
     *      as the value used to decrypt the cookie password
     * @return String encoding the input parameters, an empty string if one of
     *      the arguments equals <code>null</code>
     */
    public static String encodePasswordCookie(String username, String password, int xorMask) {
        String encoding = new String(new char[]{DELIMITER, ENCODE_CHAR_OFFSET1, ENCODE_CHAR_OFFSET2});

        return encodePasswordCookie(username, password, encoding, xorMask);
    }

    /**
     * Builds a cookie string containing a username and password, using offsets
     * to customize the encoding.
     * <p/>
     * Note: with open source this is not really secure, but it prevents users
     * from snooping the cookie file of others and by changing the XOR mask and
     * character offsets, you can easily tweak results.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param username the username
     * @param password the password
     * @param encoding a String used to customize cookie encoding (only the first 3 characters are used)
     * @param xorMask the XOR mask to encrypt the value with, must be same as
     *      as the value used to decrypt the cookie password
     * @return String encoding the input parameters, an empty string if one of
     *      the arguments equals <code>null</code>.
     */
    public static String encodePasswordCookie(String username, String password, String encoding, int xorMask) {
        StringBuffer buf = new StringBuffer();

        if (username != null && password != null) {

            char offset1 = (encoding != null && encoding.length() > 1)
                ? encoding.charAt(1) : ENCODE_CHAR_OFFSET1;

            char offset2 = (encoding != null && encoding.length() > 2)
                ? encoding.charAt(2) : ENCODE_CHAR_OFFSET2;

            byte[] bytes = (username + DELIMITER + password).getBytes();
            int b;

            for (int n = 0; n < bytes.length; n++) {
                b = bytes[n] ^ (xorMask + n);
                buf.append((char) (offset1 + (b & 0x0F)));
                buf.append((char) (offset2 + ((b >> 4) & 0x0F)));
            }
        }

        return buf.toString();
    }

    /**
     * Decodes a cookie string containing a username and password.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param cookieVal the encoded cookie username and password value
     * @param xorMask the XOR mask to decrypt the value with, must be same as
     *      as the value used to encrypt the cookie password
     * @return String[] containing the username at index 0 and the password at
     *      index 1, or <code>{ null, null }</code> if cookieVal equals
     *      <code>null</code> or the empty string.
     */
    public static String[] decodePasswordCookie(String cookieVal, int xorMask) {
        String encoding = new String(new char[]{DELIMITER, ENCODE_CHAR_OFFSET1, ENCODE_CHAR_OFFSET2});

        return decodePasswordCookie(cookieVal, encoding, xorMask);
    }

    /**
     * Decodes a cookie string containing a username and password.
     * <p/>
     * This method was derived from Atlassian <tt>CookieUtils</tt> method of
     * the same name, release under the Apache License.
     *
     * @param cookieVal the encoded cookie username and password value
     * @param encoding  a String used to customize cookie encoding (only the first 3 characters are used)
     *      - should be the same string you used to encode the cookie!
     * @param xorMask the XOR mask to decrypt the value with, must be same as
     *      as the value used to encrypt the cookie password
     * @return String[] containing the username at index 0 and the password at
     *      index 1, or <code>{ null, null }</code> if cookieVal equals
     *      <code>null</code> or the empty string.
     */
    public static String[] decodePasswordCookie(String cookieVal, String encoding,
            int xorMask) {

        // Check that the cookie value isn't null or zero-length
        if (cookieVal == null || cookieVal.length() <= 0) {
            return null;
        }

        char offset1 = (encoding != null && encoding.length() > 1)
            ? encoding.charAt(1) : ENCODE_CHAR_OFFSET1;

        char offset2 = (encoding != null && encoding.length() > 2)
            ? encoding.charAt(2) : ENCODE_CHAR_OFFSET2;

        // Decode the cookie value
        char[] chars = cookieVal.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        int b;

        for (int n = 0, m = 0; n < bytes.length; n++) {
            b = chars[m++] - offset1;
            b |= (chars[m++] - offset2) << 4;
            bytes[n] = (byte) (b ^ (xorMask + n));
        }

        cookieVal = new String(bytes);
        int pos = cookieVal.indexOf(DELIMITER);

        String username = (pos < 0) ? "" : cookieVal.substring(0, pos);
        String password = (pos < 0) ? "" : cookieVal.substring(pos + 1);

        return new String[]{username, password};
    }

    /**
     * Return a HTML escaped string for the given string value.
     *
     * @param value the string value to escape
     * @return the HTML escaped string value
     */
    public static String escapeHtml(String value) {
        if (requiresHtmlEscape(value)) {

            HtmlStringBuffer buffer = new HtmlStringBuffer(value.length() * 2);

            buffer.appendEscaped(value);

            return buffer.toString();

        } else {
            return value;
        }
    }

    /**
     * Return the value string limited to maxlength characters. If the string
     * gets curtailed, "..." is appended to it.
     * <p/>
     * Adapted from Velocity Tools Formatter.
     *
     * @param value the string value to limit the length of
     * @param maxlength the maximum string length
     * @return a length limited string
     */
    public static String limitLength(String value, int maxlength) {
        return limitLength(value, maxlength, "...");
    }

    /**
     * Return the value string limited to maxlength characters. If the string
     * gets curtailed and the suffix parameter is appended to it.
     * <p/>
     * Adapted from Velocity Tools Formatter.
     *
     * @param value the string value to limit the length of
     * @param maxlength the maximum string length
     * @param suffix the suffix to append to the length limited string
     * @return a length limited string
     */
    public static String limitLength(String value, int maxlength, String suffix) {
        String ret = value;
        if (value.length() > maxlength) {
            ret = value.substring(0, maxlength - suffix.length()) + suffix;
        }
        return ret;
    }


    /**
     * Return an ordered map of request parameters from the given request.
     *
     * @param request the servlet request to obtain request parameters from
     * @return the ordered map of request parameters
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> getRequestParameters(HttpServletRequest request) {

        TreeMap<String, String> requestParams = new TreeMap<String, String>();

        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement().toString();

            String[] values = request.getParameterValues(name);
            HtmlStringBuffer valsBuffer = new HtmlStringBuffer(32);

            if (values.length == 1) {
                valsBuffer.append(values[0]);

            } else {
                for (int i = 0; i < values.length; i++) {
                    if (i == 0) {
                        valsBuffer.append("[");
                    }
                    valsBuffer.append(values[i]);
                    if (i == values.length - 1) {
                        valsBuffer.append("]");
                    } else {
                        valsBuffer.append(",");
                    }
                }
            }
            requestParams.put(name, valsBuffer.toString());
        }

        return requestParams;
    }

    /**
     * Return the page resource path from the request. For example:
     * <pre class="codeHtml">
     * <span class="blue">http://www.mycorp.com/banking/secure/login.htm</span>  ->  <span class="red">/secure/login.htm</span> </pre>
     *
     * @param request the page servlet request
     * @return the page resource path from the request
     */
    public static String getResourcePath(HttpServletRequest request) {
        // Adapted from VelocityViewServlet.handleRequest() method:

        // If we get here from RequestDispatcher.include(), getServletPath()
        // will return the original (wrong) URI requested.  The following
        // special attribute holds the correct path.  See section 8.3 of the
        // Servlet 2.3 specification.

        String path = (String)
            request.getAttribute("javax.servlet.include.servlet_path");

        // Also take into account the PathInfo stated on
        // SRV.4.4 Request Path Elements.
        String info = (String)
            request.getAttribute("javax.servlet.include.path_info");

        if (path == null) {
            path = request.getServletPath();
            info = request.getPathInfo();
        }

        if (info != null) {
            path += info;
        }

        return path;
    }

    /**
     * Return the getter method name for the given property name.
     *
     * @param property the property name
     * @return the getter method name for the given property name.
     */
    public static String toGetterName(String property) {
        HtmlStringBuffer buffer = new HtmlStringBuffer(property.length() + 3);

        buffer.append("get");
        buffer.append(Character.toUpperCase(property.charAt(0)));
        buffer.append(property.substring(1));

        return buffer.toString();
    }

    /**
     * Return the is getter method name for the given property name.
     *
     * @param property the property name
     * @return the is getter method name for the given property name.
     */
    public static String toIsGetterName(String property) {
        HtmlStringBuffer buffer = new HtmlStringBuffer(property.length() + 3);

        buffer.append("is");
        buffer.append(Character.toUpperCase(property.charAt(0)));
        buffer.append(property.substring(1));

        return buffer.toString();
    }

    /**
     * Return a field label string from the given field name. For example:
     * <pre class="codeHtml">
     * <span class="blue">faxNumber</span> &nbsp; -&gt; &nbsp; <span class="red">Fax Number</span> </pre>
     *
     * @param name the field name
     * @return a field label string from the given field name
     */
    public static String toLabel(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null name parameter");
        }

        HtmlStringBuffer buffer = new HtmlStringBuffer();

        for (int i = 0, size = name.length(); i < size; i++) {
            char aChar = name.charAt(i);

            if (i == 0) {
                buffer.append(Character.toUpperCase(aChar));

            } else {
                buffer.append(aChar);

                if (i < name.length() - 1) {
                    char nextChar = name.charAt(i + 1);
                    if (Character.isLowerCase(aChar)
                        && (Character.isUpperCase(nextChar)
                            || Character.isDigit(nextChar))) {
                       buffer.append(" ");
                    }
                }
            }
        }

        return buffer.toString();
    }

    /**
     * Return an 32 char MD5 encoded string from the given plain text.
     * The returned value is MD5 hash compatible with Tomcat catalina Realm.
     * <p/>
     * Adapted from <tt>org.apache.catalina.util.MD5Encoder</tt>
     *
     * @param plaintext the plain text value to encode
     * @return encoded MD5 string
     */
    public static String toMD5Hash(String plaintext) {
        if (plaintext == null) {
            throw new IllegalArgumentException("Null plaintext parameter");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(plaintext.getBytes("UTF-8"));

            byte[] binaryData = md.digest();

            char[] buffer = new char[32];

            for (int i = 0; i < 16; i++) {
                int low = (int) (binaryData[i] & 0x0f);
                int high = (int) ((binaryData[i] & 0xf0) >> 4);
                buffer[i * 2] = HEXADECIMAL[high];
                buffer[i * 2 + 1] = HEXADECIMAL[low];
            }

            return new String(buffer);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return a field name string from the given field label.
     * <p/>
     * A label of " OK do it!" is returned as "okDoIt". Any &amp;nbsp;
     * characters will also be removed.
     * <p/>
     * A label of "customerSelect" is returned as "customerSelect".
     *
     * @param label the field label or caption
     * @return a field name string from the given field label
     */
    public static String toName(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Null label parameter");
        }

        boolean doneFirstLetter = false;
        boolean lastCharBlank = false;
        boolean hasWhiteSpace = (label.indexOf(' ') != -1);

        HtmlStringBuffer buffer = new HtmlStringBuffer(label.length());
        for (int i = 0, size = label.length(); i < size; i++) {
            char aChar = label.charAt(i);

            if (aChar != ' ') {
                if (Character.isJavaIdentifierPart(aChar)) {
                    if (lastCharBlank) {
                        if (doneFirstLetter) {
                            buffer.append(Character.toUpperCase(aChar));
                            lastCharBlank = false;
                        } else {
                            buffer.append(Character.toLowerCase(aChar));
                            lastCharBlank = false;
                            doneFirstLetter = true;
                        }
                    } else {
                        if (doneFirstLetter) {
                            if (hasWhiteSpace) {
                                buffer.append(Character.toLowerCase(aChar));
                            } else {
                                buffer.append(aChar);
                            }
                        } else {
                            buffer.append(Character.toLowerCase(aChar));
                            doneFirstLetter = true;
                        }
                    }
                }
            } else {
                lastCharBlank = true;
            }
        }

        return buffer.toString();
    }

    /**
     * Return the setter method name for the given property name.
     *
     * @param property the property name
     * @return the setter method name for the given property name.
     */
    public static String toSetterName(String property) {
        HtmlStringBuffer buffer = new HtmlStringBuffer(property.length() + 3);

        buffer.append("set");
        buffer.append(Character.toUpperCase(property.charAt(0)));
        buffer.append(property.substring(1));

        return buffer.toString();
    }

	public static String splitByUpperCase(String name, boolean isUpperCase) {
		StringBuilder builder = new StringBuilder();
		boolean lastUpperCase = true;
		for (int i = 0, size = name.length(); i < size; i++) {
			char ch = name.charAt(i);
			if (Character.isUpperCase(ch)) {
				if (!lastUpperCase) {
					builder.append('_');
				}
			}
			builder.append(ch);
			lastUpperCase = Character.isUpperCase(ch);
		}
		if(isUpperCase){
			return builder.toString().toUpperCase();
		}
		return builder.toString().toLowerCase();
	}
	
    // -------------------------------------------------------- Package Methods

    /**
     * Return true if the given character requires HTML escaping.
     *
     * @param aChar the character value to test
     * @return true if the given character requires HTML escaping
     */
    static boolean requiresEscape(char aChar) {
        int index = aChar;

        if (index < HTML_ENTITIES.length - 1) {
            return HTML_ENTITIES[index] != null;

        } else {
            return false;
        }
    }

    /**
     * Return the HTML escaped string for the given character value.
     *
     * @param aChar the character value to escape
     * @return the HTML escaped string for the given character value
     */
    static String escapeChar(char aChar) {
        int index = aChar;

        if (index < HTML_ENTITIES.length - 1 && HTML_ENTITIES[index] != null) {
            return HTML_ENTITIES[index];

        } else {
            return String.valueOf(aChar);
        }
    }

    // -------------------------------------------------------- Private Methods

    @SuppressWarnings({ "unchecked", "unused" })
	private static Set getObjectPropertyNames(Object object) {
        if (object instanceof Map) {
            return ((Map) object).keySet();
        }

        HashSet hashSet = new HashSet();

        Method[] methods = object.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();

            if (methodName.startsWith("get") && methodName.length() > 3) {
                String propertyName =
                    "" + Character.toLowerCase(methodName.charAt(3))
                    + methodName.substring(4);
                hashSet.add(propertyName);
            }
            if (methodName.startsWith("is") && methodName.length() > 2) {
                String propertyName =
                    "" + Character.toLowerCase(methodName.charAt(2))
                    + methodName.substring(3);
                hashSet.add(propertyName);
            }
            if (methodName.startsWith("set") && methodName.length() > 3) {
                String propertyName =
                    "" + Character.toLowerCase(methodName.charAt(3))
                    + methodName.substring(4);
                hashSet.add(propertyName);
            }
        }

        return hashSet;
    }

    /**
     * Return true if the given string requires HTML escaping of characters.
     *
     * @param value the string value to test
     * @return true if the given string requires HTML escaping of characters
     */
    private static boolean requiresHtmlEscape(String value) {
        if (value == null) {
            return false;
        }

        int length = value.length();
        for (int i = 0; i < length; i++) {
            if (requiresEscape(value.charAt(i))) {
                return true;
            }
        }

        return false;
    }
    
    public static String getMakeLiner(String temp, int widLimitMetric) {
    	if(temp == null)
    	{
    		return null;
    	}
        String strLiner = "";
        Font ft = new Font("Arial, Helvetica, sans-serif", Font.TRUETYPE_FONT, 9);
        JComponent jx = new JComponent() {
			private static final long serialVersionUID = 2792190024722186161L;
		};
		jx.setFont(ft);
        FontMetrics fm = jx.getFontMetrics(ft);
        int strWidMetric = fm.stringWidth(temp);
        while (strWidMetric > 0) {
            char[] ch = temp.toCharArray();
            if (strWidMetric > widLimitMetric) {
                int wChTot = 0;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; wChTot < widLimitMetric; i++) {
                    wChTot += fm.charWidth(ch[i]);
                    sb.append(ch[i]);
                }

                String strWid = sb.toString();
                int lastChar13 = strWid.lastIndexOf(" ");
                if (lastChar13 != -1) {
                    strWid = temp.substring(0, lastChar13);
                    strLiner = strLiner + strWid + "<br/>";
                    temp = temp.substring(lastChar13 + 1, temp.length());
                    strWidMetric = fm.stringWidth(temp);
                } else {
                    strWid = temp.substring(0, strWid.length());
                    strLiner = strLiner + strWid + "<br/>";
                    temp = temp.substring(strWid.length(), temp.length());
                    strWidMetric = fm.stringWidth(temp);
                }
            } else {
                strLiner = strLiner + temp;
                temp = "";
                strWidMetric = fm.stringWidth(temp);
            }

        }
        return strLiner;
    }
    
    public static String rpad(String string, String format, int len) {
		StringBuffer buffer = null;
		if(string == null || string.equals("")){
			buffer = new StringBuffer();
		}else{
			buffer = new StringBuffer(string);	
		}
		
		while (buffer.length() < len) {
			buffer.append(format);
		}
		return buffer.toString();
	}

	/**
	 * To left pad the string with the format until it achieve the maximum length that specified
	 * @param string
	 * @param format
	 * @param len
	 * @return
	 */
	public static String lpad(String string, String format, int len) {
		StringBuffer buffer = new StringBuffer(string);
		while (buffer.length() < len) {
			buffer.insert(0, format);
		}
		return buffer.toString();
	}
	
		
	/**
	 * Escape special character '%' for query 'like'
	 */
	public static final String escapeSQLChar = "$";
	public static final String escapeSQLLike = " ESCAPE '"+escapeSQLChar+"'";
	
	public static String escapeSQLValue(String value){
		return value.replace(escapeSQLChar, escapeSQLChar+escapeSQLChar).replaceAll("\\%", "\\"+escapeSQLChar+"\\%");
	}
	
	public static String escapeSQLLike(String value){
		return value.concat(" ESCAPE '$'");
	}
	
    public static boolean isNullorEmptyString(String value){
		return value == null || value.length() == 0;
	}
}

