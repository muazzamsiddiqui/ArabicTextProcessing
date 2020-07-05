/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arabictextprocessing;

/**
 *
 * @author Muazzam Siddiqui
 */
public class Constants {

    public static final String arabicEncoding = "UTF-8";
    public static final char[] DIACRITICS = {'َ', 'ُ', 'ِ', 'ّ', 'ٌ', 'ً', 'ٍ', 'ْ', 'ٰ', 'ّ'};
    public static final String[] DIACRITICS_HEX = {
    "064b", "064c", "064d", "064e", "064f", "0650", "0651", "0652", "0653",
    "0654", "0655"
    };
    public static final String[] QURAN_STOPWORDS = {
    "الذي","ولا","افلا","او","اذا","الا","الي","ان","انا",
    "به","بهم","ثم","حتى","ذلك","علي","عن","فاما","فلا","فهو","في","كان","لو","ما",
    "من","هم","واما","وانه","ولا","وما","فمن","واذا","ومن","له","نحن","التي","انها",
    "لهم","فلم","لا","اني","الم","ان","قد","وقد","واني","مما","انه","انك","انهم","لم",
    "منها","مع","لن","منها","انما","انه","وانه","ولن","وانا","وان","فان","اليهم",
    "لك","لكم","بما","هو","فقد","انت","وان","الذين","عليهم","وهو","ولو","وهم","اذ","يا"};
    public static final String[] STOPWORDS = {"ابو", "احد", "اذ", "اذا", "اصبح",
    "اضحي", "ال", "الا", "التي", "الحالي", "الذي", "الذين", "اللذين", "الي",
    "اليه", "اليها", "اليوم", "اما", "امام", "امسي", "ان", "انه", "او", "اول",
    "اي", "بات", "بان", "بد", "بدلا", "بعد", "بل", "به", "بها", "بهذا", "بين",
    "تحت", "تكون", "تلك", "ثم", "جدا", "حتي", "حول", "حيث", "حين", "دون", "ذات",
    "ذلك", "ستكون", "صار", "ضد", "ضمن", "ظل", "علي", "عليه", "عليها", "عن", "عند",
    "عنه", "عنها", "فان", "فانه", "فقد", "فقط", "فكان", "فهو", "في", "فيه", "فيها",
    "قبل", "قد", "كان", "كانت", "كذلك", "كل", "كما", "كيف", "لا", "لازال", "لاسيما",
    "لان", "لايزال", "لدي", "لذلك", "لعل", "لكن", "لكنه", "لم", "لن", "له", "لها",
    "لهذا", "لهم", "لو", "ليت", "ليس", "ليسب", "ما", "ماانفك", "مابرح", "مازال",
    "مافتي", "مايزال", "مساء", "مع", "معه", "مما", "من", "منذ", "منه", "منها",
    "نحو", "هذا", "هذه", "هل", "هن", "هنا", "هناك", "هو", "هولاء", "هي", "و", "وابو",
    "والتي", "والذي", "وان", "وبين", "وثي", "وعلي", "وفي", "وقد", "وكان", "وكانت",
    "ولايزال", "ولكن", "وله", "وليس", "ومع", "ومن", "وهذا", "وهو", "وهي", "يكون",
    "يلي", "يمكن", "يوم"};
    public static final char[] SAALTAMONIHA = {'س','أ','ل','ت','م','و','ن','ي','ه','ا','ة'};
    public static final char[] ARABIC_ALPHABETS = {'ء', 'ا', 'إ', 'أ', 'آ', 'ب',
    'ة', 'ت', 'ث', 'ج', 'ح', 'خ', 'د', 'ذ', 'ر', 'ز', 'س', 'ش', 'ص', 'ض', 'ط',
    'ظ', 'ع', 'غ', 'ف', 'ق', 'ك', 'ل', 'م', 'ن', 'ه', 'و', 'ؤ', 'ى', 'ي', 'ئ'};

  public static final String[] ENGLISH_STOPWORDS = {"a","about","after","all","also",
  "amp","an","and","any","are","as","at","be","because","been","but","by","can",
  "co","corp","could","for","from","gt","had","has","have","he","her","his","http",
  "if","in","inc","into","is","it","its","laquo","last","ldquo","lsquo","lt",
  "mdash","more","most","mr","mrs","ms","mz","nbsp","ndash","no","not","of",
  "on","one","only","or","other","out","over","quot","raquo","rdquo","rsquo",
  "s","says","she","so","some","such","than","that","the","their","then",
  "there","these","they","this","to","up","was","we","were","when","which",
  "who","will","with","would","www"};
  
  public static final String[] URDU_STOPWORDS = {
  "آج","اب","ابھی","اپنا","اپنے","اپنی","اپنوں","اس","اسے","اگر","ایسا","ایسے"," ایسی","ایسوں","ایک",
  "اہم","بار","بارے","بعد","ان","انہیں","انہوں","بھی","بہت","پر","پھر","تاہم","تعلق","تمام","تو","تک",
  "تب","جاری","تھا","تھی","تھے","تھیں","جبکہ","جس","جن","جسے","جو","جب","جہاں","چند","خلاف",
  "خود","درمیان","دو","دور","دوسرا","دوسرے","دوسری","دوسروں","دونوں","زیادہ","سے","ساتھ","سال",
  "شروع","سب","سوا","سکتا","سکتی","سکتے","سکتیں","سکا","سکی","سکیں","سکے","سکنا","سکنے",
  "سکوں","سکو"," سک","طرح","طور","غیر","قبل","گزشتہ","لوگ","لیکن","میں","مسٹر","مطابق","نہ","نہیں",
  "نے","نام","وہ","والا","والی","والے","والوں","والیوں","کو","کا","کی","کے","کچھ","کرتا","کرتی","کرتے",
  "کرتیں","کرنا","کرنے","کرنی","کیا","کئے","کیں","کی","کروں","کرے","کرو","کر","کس","کسے","کسی","کم",
  "ہزار"," ہو","ہوئی","ہوا","ہوۓ","ہوئیں","ہوں","ہے","ہوتا","ہوتی","ہوتے","ہوتیں","ہونا","ہونی",
  "ہونے","ہی","وہاں","کہاں","وہی","کیسے","کیوں","یہی","کب","کبھی","کہ","جب","اسی"," ہر",
  "کئی","تقریباً","تاکہ","مگر","چکا","چکی ","چکے ","چکیں ","چکتا ","چکتی ","چکتے ","چکتیں ",
   "چکوں","چکو","چک","رہا ","رہی ","رہے", "اور", "ہیں", "یہ", "ہو", 
  };

  public static final String[] QURAN_STOPWORDS_BIGLIST = {"بالله","ربكما","ربك","ربه","ربك","والله","الله","ربي",
"ءالـٔن",
"ءاناء",
"ءانت",
"ءانتم",
"الٓر",
"الٓمٓ",
"الٓمٓر",
"الٓمٓصٓ",
"اءذا",
"اءنا",
"اءنك",
"ايذا",
"اين",
"اينا",
"اينكم",
"ابدۢا",
"ابدا",
"اثم",
"احقابا",
"ادني",
"اذلك",
"اسفل",
"اعندهۥ",
"افانت",
"افانتم",
"افاي۟ن",
"افبهذا",
"افكلما",
"افلا",
"افلم",
"افما",
"افمن",
"افهم",
"افي",
"الا",
"الكم",
"الم",
"الن",
"الهم",
"ام",
"امامهۥ",
"اما",
"اماذا",
"امن",
"ان",
"انۢ",
"انا",
"انا۠",
"انحن",
"انك",
"انكم",
"انما",
"اننا",
"انها",
"انه",
"انهۥ",
"انهم",
"انهما",
"اني",
"انيٓ",
"انت",
"انتم",
"انتما",
"اهٓولاء",
"اهذا",
"اهكذا",
"اهم",
"او",
"اوكلما",
"اولا",
"اولم",
"اولما",
"اولو",
"اومن",
"اي",
"اينما",
"اياما",
"ايان",
"ايما",
"ايكم",
"ايهم",
"او۟لاء",
"او۟لٓيك",
"او۟لٓيكم",
"اذ",
"اذا",
"الي",
"اليٓ",
"اليك",
"اليكم",
"اليكما",
"الينا",
"اليها",
"اليه",
"اليهم",
"اليهن",
"انني",
"اننيٓ",
"انهۥٓ",
"انهن",
"اياك",
"اياكم",
"ايانا",
"اياه",
"ٱلـٔن",
"ٱلءان",
"ٱليوم",
"ٱلٓـٔي",
"ٱلتي",
"ٱلتيٓ",
"ٱلذين",
"ٱلذي",
"ٱلذيٓ",
"بعد",
"بعدما",
"بعدها",
"بعدهۥ",
"بعدهن",
"بل",
"بلي",
"بليٓ",
"بين",
"بينك",
"بينكم",
"بيننا",
"بينها",
"بينهۥ",
"بينهم",
"بينهما",
"بينهن",
"بيني",
"بكرة",
"بان",
"بانا",
"بانكم",
"باننا",
"بانهۥ",
"بانهۥٓ",
"بانهم",
"باي",
"باييكم",
"بٱلتي",
"بٱلذي",
"بٱلذيٓ",
"بٱلذين",
"بذلك",
"بك",
"بكم",
"بم",
"بما",
"بمن",
"بنا",
"بها",
"بهذا",
"بهۦ",
"به",
"بهۦٓ",
"بهم",
"بهما",
"بهن",
"بي",
"بيٓ",
"تحت",
"تحتك",
"تلك",
"تلكما",
"تلكم",
"ثم",
"جانب",
"حتي",
"حتيٓ",
"حول",
"حولهۥ",
"حولين",
"حيث",
"حينيذ",
"حمٓ",
"خلفنا",
"خلفها",
"خلفهم",
"دون",
"ذا",
"ذات",
"ذلك",
"ذلكم",
"ذلكما",
"سبع",
"سوف",
"سنين",
"شطر",
"شطرهۥ",
"صٓ",
"صبحا",
"ضحي",
"طسٓ",
"طسٓمٓ",
"طه",
"عٓسٓقٓ",
"عشرا",
"علي",
"عليٓ",
"عليك",
"عليكم",
"عليكما",
"علينا",
"عليها",
"عليه",
"عليهم",
"عليهما",
"عليهن",
"عم",
"عما",
"عن",
"عنۢ",
"عنها",
"عنه",
"عنهم",
"عنهما",
"عنا",
"عني",
"عنيٓ",
"عنك",
"عنكم",
"عند",
"عندك",
"عندكم",
"عندنا",
"عندها",
"عندهۥ",
"عندهۥٓ",
"عندهم",
"عندي",
"عنديٓ",
"فاما",
"فانا۠",
"فان",
"فانهۥ",
"فاني",
"فانت",
"فانتم",
"فاين",
"فاينما",
"فاو۟لٓيك",
"فاذا",
"فاذ",
"فالينا",
"فاليه",
"فالم",
"فانۢ",
"فانا",
"فانك",
"فانكم",
"فانما",
"فانها",
"فانهۥٓ",
"فانهم",
"فانيٓ",
"فايي",
"فٱلـٔن",
"فٱليوم",
"فٱلذين",
"فباي",
"فبذلك",
"فبم",
"فبما",
"فتلك",
"فثم",
"فذلك",
"فذلكم",
"فذلكن",
"فذنك",
"فسوف",
"فعلي",
"فعليكم",
"فعليها",
"فعليه",
"فعليهم",
"فعليهن",
"فعند",
"ففي",
"فقد",
"فكانما",
"فكذلك",
"فكيف",
"فلا",
"فلسوف",
"فلعلك",
"فلكم",
"فلم",
"فلما",
"فلن",
"فلها",
"فلهۥ",
"فله",
"فلهۥٓ",
"فلهما",
"فلهم",
"فلهن",
"فلو",
"فلولا",
"فلذلك",
"فما",
"فماذا",
"فمال",
"فمن",
"فمنۢ",
"فمنها",
"فمنه",
"فمنهم",
"فمنكم",
"فهذا",
"فهل",
"فهم",
"فهو",
"فهي",
"فوق",
"فوقكم",
"فوقها",
"فوقهم",
"فيوميذ",
"في",
"فيٓ",
"فيكم",
"فيم",
"فيما",
"فينا",
"فيها",
"فيه",
"فيهۦ",
"فيهم",
"فيهما",
"فيهن",
"قٓ",
"قبل",
"قبلك",
"قبلهۥ",
"قبلهم",
"قبلي",
"قد",
"كٓهيعٓصٓ",
"كان",
"كانك",
"كانما",
"كانها",
"كانهۥ",
"كانهم",
"كانهن",
"كٱلتي",
"كٱلذي",
"كٱلذين",
"كذلك",
"كذلكم",
"كلا",
"كم",
"كما",
"كمن",
"كمنۢ",
"كي",
"كيف",
"كلما",
"لين",
"لينۢ",
"لا",
"لانت",
"لانتم",
"لالي",
"لكن",
"لدا",
"لدي",
"لدينا",
"لديه",
"لديهم",
"لسوف",
"لعلي",
"لعل",
"لعلك",
"لعلكم",
"لعلنا",
"لعلهۥ",
"لعلهم",
"لعليٓ",
"لفي",
"لقد",
"لك",
"لكم",
"لكما",
"للذي",
"للذين",
"لما",
"لمع",
"لمعكم",
"لمن",
"لمنكم",
"لم",
"لن",
"لنا",
"لنحن",
"لها",
"لهۥ",
"له",
"لهۥٓ",
"لهم",
"لهما",
"لهن",
"لهو",
"لهي",
"لو",
"لولا",
"ليتني",
"ليلا",
"ليلة",
"لان",
"لكي",
"لكيلا",
"للتي",
"للذيٓ",
"لهذا",
"لي",
"ليٓ",
"لكنا۠",
"ما",
"ماذا",
"مال",
"متي",
"مع",
"معك",
"معكم",
"معكما",
"معنا",
"معهۥ",
"معه",
"معهۥٓ",
"معهم",
"معي",
"مكانا",
"من",
"منۢ",
"مهما",
"مم",
"مما",
"ممن",
"منها",
"منه",
"منهم",
"منهما",
"منهن",
"منا",
"مني",
"منيٓ",
"منك",
"منكم",
"منكن",
"معها",
"نٓ",
"نحن",
"نعم",
"هٓولاء",
"هٓانتم",
"هتين",
"هذا",
"هذان",
"هذن",
"هذه",
"هذهۦ",
"هذهۦٓ",
"ههنا",
"هل",
"هم",
"هما",
"هنالك",
"هن",
"هو",
"هي",
"هيه",
"واصيلا",
"والو",
"واما",
"وان",
"وانا",
"وانا۠",
"وانك",
"وانكم",
"وانهۥ",
"وانهۥٓ",
"وانهم",
"واني",
"وانت",
"وانتم",
"واو۟لٓيك",
"واو۟لٓيكم",
"واذا",
"واذ",
"والي",
"واليٓ",
"واليك",
"والينا",
"واليه",
"والا",
"وانما",
"واننا",
"وانني",
"وانها",
"وانهما",
"وانيٓ",
"واياك",
"واياكم",
"واياهم",
"وايي",
"وٱلٓـٔي",
"وٱلتي",
"وٱلتيٓ",
"وٱلذان",
"وٱلذي",
"وٱلذيٓ",
"وٱلذين",
"وبين",
"وبينك",
"وبينكم",
"وبينهۥ",
"وبينهۥٓ",
"وبينهم",
"وبينهما",
"وبٱلذي",
"وبذلك",
"وبما",
"وبمن",
"وبهۦ",
"وتلك",
"وثمنية",
"وثلثهۥ",
"وحيث",
"وحين",
"ودون",
"وذلك",
"وذلكم",
"وراء",
"وراءكم",
"وراءهۥ",
"وراءهم",
"وسوف",
"وعشيا",
"وعلي",
"وعليٓ",
"وعليكم",
"وعلينا",
"وعليها",
"وعليه",
"وعليهم",
"وعن",
"وعند",
"وعندنا",
"وعندهۥ",
"وعندهۥٓ",
"وعندهم",
"وفي",
"وفيٓ",
"وفيكم",
"وفيها",
"وفيه",
"وقبل",
"وقد",
"وكذلك",
"وكم",
"وكيف",
"وكلما",
"ولين",
"ولا",
"ولكن",
"ولكنۢ",
"ولكنا",
"ولكنكم",
"ولكنهۥٓ",
"ولكنهم",
"ولكني",
"ولكنيٓ",
"ولدينا",
"ولسوف",
"ولعلكم",
"ولعلهم",
"ولقد",
"ولك",
"ولكم",
"ولمن",
"ولم",
"ولما",
"ولن",
"ولنا",
"ولها",
"ولهۥ",
"وله",
"ولهۥٓ",
"ولهم",
"ولهن",
"ولو",
"ولولا",
"ولذلك",
"وللذين",
"ولي",
"وما",
"وماذا",
"ومن",
"ومنۢ",
"ومما",
"وممن",
"ومنها",
"ومنه",
"ومنهم",
"ومنا",
"ومنك",
"ومنكم",
"ونحن",
"ونهارا",
"ونصفهۥ",
"وهٓولاء",
"وهذا",
"وهذه",
"وهل",
"وهم",
"وهما",
"وهو",
"وهي",
"ويوم",
"ويوميذ",
"ويكان",
"ويكانهۥ",
"يٓايتها",
"يٓايه",
"يٓايها",
"يليت",
"يليتنا",
"يليتني",
"يليتها",
"يوم",
"يوميذ",
"يوميذۭ",
"الذين",
"الذي",
"لله",
"كانوا",
"كنتم",
"ربكم",
"وكان",
"اوليك",
"يسٓ"};
  public static final String PATH_TO_STEMMER_FILES = "/Packages/khojaStemmer/StemmerFiles/";
  public static final String PATH_TO_TAGGER_MODELS = "/Packages/stanford-postagger-full-2012-03-09/models/";
}