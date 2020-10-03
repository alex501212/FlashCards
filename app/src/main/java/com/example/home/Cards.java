package com.example.home;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


// Card Example/Explanation
// front - front contents of card
// back - back contents of card
// "new" - states whether the card is new or old
// "0.5" - day interval of the card
// reviewDay - day to review
// "waiting" - status of the card in the review queue
// reading - hiragana reading

public class Cards
{
    public static List<String[]> preLoadedCards = new ArrayList<String[]>();
    public static List<String[]> cards = new ArrayList<String[]>();
    public static List<String[]> reviewedCards = new CopyOnWriteArrayList<String[]>(); // CopyOnWriteArrayList handles java.util.ConcurrentModificationException

    public void addCards(String front, String back, String reading)
    {
        Calendar currentDate = Calendar.getInstance();
        int dateForCard = currentDate.get(Calendar.DAY_OF_YEAR); // current date which the interval will be added to
        String reviewDay = String.valueOf(dateForCard);

        cards.add(new String[]{front, back, "new", "0.5", reviewDay, "waiting", reading}); // add new card to cards
    }

    public void reviewed(String front, String back, String interval, String reviewDay, String reading)
    {
        reviewedCards.add(new String[]{front, back, "rev", interval, reviewDay, "waiting", reading}); // add reviewed card to reviewed cards
    }

    public List<String[]> getNew()
    {
        return cards;
    }

    public List<String[]> getReviewed()
    {
        return reviewedCards;
    }

    public List<String[]> getPreLoaded()
    {
        List<String[]> preLoaded = new ArrayList<String[]>();
        preLoaded.clear();

        for(int i = 0; i < 10; i++)
        {
            preLoaded.add(preLoadedCards.get(i));
            preLoadedCards.remove(0);
        }

        //preLoaded.add(preLoadedCards.get(0));
        //preLoaded.add(preLoadedCards.get(1));
        //preLoadedCards.remove(0);
        //preLoadedCards.remove(0);

        return preLoaded;
    }

    public List<String[]> getPreLoadedAll()
    {
        return preLoadedCards;
    }

    public void emptyCards()
    {
        cards.clear();
    }

    public void emptyReviewedCards()
    {
        reviewedCards.clear();
    }

    public void removeOld() // remove old versions of cards from reviewedCards
    {
        if(reviewedCards!=null)
        {
            // remove duplicates
            removeDupes();

            Calendar currentDate = Calendar.getInstance();
            int dayOfYearCurrent = currentDate.get(Calendar.DAY_OF_YEAR);
            //String day = String.valueOf(dayOfYearCurrent);

            // go through all cards in reviewed cards and check for older version of cards and remove them
            for (String[] card : reviewedCards) {
                // store the reviewdate of each card
                String reviewDay = card[4];
                int reviewDayInt = Integer.parseInt(reviewDay);

                // allows any older versions of cards after they are newly reviewed to removed
                if (dayOfYearCurrent >= reviewDayInt) {
                    reviewedCards.remove(card);
                }
            }
        }
    }
    public void removeDupes()
    {
        // go through all cards in reviewedCards and for each one check if it has a duplicate and if so remove it
        for (String[] card : reviewedCards)
        {
            int x = 0;
            for (String[] cards : reviewedCards)
            {
                if (Arrays.equals(card, cards))
                {
                    x += 1;
                    if (x == 2)
                    {
                        reviewedCards.remove(cards);
                    }
                }
            }
        }
    }

    // print the contents of reviewedCards
    public void printList()
    {
        // combine all attributes of each card into a single string and print Log to console
        for(String[] items : reviewedCards)
        {
            String card = items[0] + ", " + items[1] + ", " + items[2] + ", " + items[3] + ", " + items[4] + ", " + items [5] + ", " + items [6];
            Log.d("1234", "reviewedCards | " + card);
        }
    }

    public void presetCards()
    {
        preLoadedCards.add(new String[]{"魚。", "Fish.", "pre", "0.5", "1", "waiting", "さかな。"});
        preLoadedCards.add(new String[]{"魚だ。", "Is fish.", "pre", "0.5", "1", "waiting", "さかなだ。"});
        preLoadedCards.add(new String[]{"魚じゃない。", "Is not fish.", "pre", "0.5", "1", "waiting", "さかなじゃない。"});
        preLoadedCards.add(new String[]{"学生じゃない。", "Is not student.", "pre", "0.5", "1", "waiting", "がくせいじゃない。"});
        preLoadedCards.add(new String[]{"静かじゃない。", "Is not quiet.", "pre", "0.5", "1", "waiting", "しずかじゃない。"});
        preLoadedCards.add(new String[]{"魚だった。", "Was fish.", "pre", "0.5", "1", "waiting", "さかなだった。"});
        preLoadedCards.add(new String[]{"学生じゃなかった。", "Was not student.", "pre", "0.5", "1", "waiting", "がくせいじゃなかった。"});
        preLoadedCards.add(new String[]{"静かじゃなかった。", " Was not quiet.", "pre", "0.5", "1", "waiting", "しぞかじゃなかった。"});
        preLoadedCards.add(new String[]{"アリスは学生？／うん、学生。", "Are you (Alice) student? / Yeah, I am.", "pre", "0.5", "1", "waiting", "アリスはがくせい。／うん、がくせい"});
        preLoadedCards.add(new String[]{"ジムは明日？／明日じゃない。", "Jim is tomorrow? / Not tomorrow.", "pre", "0.5", "1", "waiting", "ジムはあした？／あしたじゃない。"});
        preLoadedCards.add(new String[]{"今日は試験だ。／ジムは？／ジムは明日。", "Today is exam. / What about Jim? / Jim is tomorrow.", "pre", "0.5", "1", "waiting", "あしたはしけんだ。／ジムは？／ジムはあした。"});
        preLoadedCards.add(new String[]{"アリスは学生？／うん、トムも学生。", "Are you (Alice) student? / Yes, and Tom is also student.", "pre", "0.5", "1", "waiting", "アリスはがくせい？／うん、トムもがくせい。"});
        preLoadedCards.add(new String[]{"アリスは学生？／うん、でもトムは学生じゃない。", "Are you (Alice) student? / Yes, but Tom is not student.", "pre", "0.5", "1", "waiting", "アリスはがくせい？／うん、でもトムはがくせいじゃない。"});
        preLoadedCards.add(new String[]{"アリスは学生？／ううん、トムも学生じゃない。", "Are you (Alice) student? / No, and Tom is also not student.", "pre", "0.5", "1", "waiting", "アリスはがくせい？／ううん、トムもがくせいじゃない。"});
        preLoadedCards.add(new String[]{"誰が学生？／ジムが学生。", "Who is the one that is student? / Jim is the one who is the student.", "pre", "0.5", "1", "waiting", "だれががくせい？／ジムはがくせい。"});
        preLoadedCards.add(new String[]{"誰が学生？", "Who is the one that is student?", "pre", "0.5", "1", "waiting", "だれががくせい？"});
        preLoadedCards.add(new String[]{"学生は誰？", "(The) student is who?", "pre", "0.5", "1", "waiting", "がくせいはだれ？"});
        preLoadedCards.add(new String[]{"静かな人。", "Quiet person.", "pre", "0.5", "1", "waiting", "しずかなひと。"});
        preLoadedCards.add(new String[]{"友達は親切。", "Friend is kind.", "pre", "0.5", "1", "waiting", "ともだちはしんせつ。"});
        preLoadedCards.add(new String[]{"友達は親切な人。", "Friend is kind person.", "pre", "0.5", "1", "waiting", "ともだちはしんせつひと。"});
        preLoadedCards.add(new String[]{"ボブは魚が好きだ。", "Bob likes fish.", "pre", "0.5", "1", "waiting", "ボブはさかながすきだ。"});
        preLoadedCards.add(new String[]{"ボブは魚が好きじゃない。", "Bob does not like fish.", "pre", "0.5", "1", "waiting", "ボブはさかながすきじゃない。"});
        preLoadedCards.add(new String[]{"ボブは魚が好きだった。", "Bob liked fish.", "pre", "0.5", "1", "waiting", "ボブはさかながすきだった。"});
        preLoadedCards.add(new String[]{"ボブは魚が好きじゃなかった。", "Bob did not like fish.", "pre", "0.5", "1", "waiting", "ボブはさかながすきじゃなかった。"});
        preLoadedCards.add(new String[]{"魚が好きなタイプ。", "Type that likes fish.", "pre", "0.5", "1", "waiting", "さかながすきなタイプ。"});
        preLoadedCards.add(new String[]{"魚が好きじゃないタイプ。", "Type that does not like fish.", "pre", "0.5", "1", "waiting", "さかながすきじゃないタイプ。"});
        preLoadedCards.add(new String[]{"魚が好きだったタイプ。", "Type that liked fish.", "pre", "0.5", "1", "waiting", "さかながすきだったタイプ。"});
        preLoadedCards.add(new String[]{"魚が好きじゃなかったタイプ。", "Type that did not like fish.", "pre", "0.5", "1", "waiting", "さかながすきじゃなかったタイプ。"});
        preLoadedCards.add(new String[]{"魚が好きじゃないタイプは、肉が好きだ。", "Types (of people) who do not like fish like meat.", "pre", "0.5", "1", "waiting", "さかながすきじゃないタイプは、にくがすきだ。"});
        preLoadedCards.add(new String[]{"高いビル。", "Tall building.", "pre", "0.5", "1", "waiting", "たかいビル。"});
        preLoadedCards.add(new String[]{"高くないビル。", "Not tall building.", "pre", "0.5", "1", "waiting", "たかくないビル。"});
        preLoadedCards.add(new String[]{"高かったビル。", "Building that was tall.", "pre", "0.5", "1", "waiting", "たかかったビル。"});
        preLoadedCards.add(new String[]{"高くなかったビル。", "Building that was not tall.", "pre", "0.5", "1", "waiting", "たかくなかったビル。"});
        preLoadedCards.add(new String[]{"静かな高いビル。", "A quiet, tall building.", "pre", "0.5", "1", "waiting", "すずかなたかいビル。"});
        preLoadedCards.add(new String[]{"高くない静かなビル。", "A not tall, quiet building.", "pre", "0.5", "1", "waiting", "たかくないしずかなビル。"});
        preLoadedCards.add(new String[]{"値段が高いレストランはあまり好きじゃない。", "Don't like high price restaurants very much.", "pre", "0.5", "1", "waiting", "ねだんがたかいレストランはあまりすきじゃない。"});
        preLoadedCards.add(new String[]{"値段があんまりよくない。", "Price isn't very good.", "pre", "0.5", "1", "waiting", "ねだんがあんまりよくない。"});
        preLoadedCards.add(new String[]{"彼はかっこよかった！", "He looked really cool!", "pre", "0.5", "1", "waiting", "かれはかっこよかった。"});
        preLoadedCards.add(new String[]{"食べる。", "Eat.", "pre", "0.5", "1", "waiting", "たべる。"});
        preLoadedCards.add(new String[]{"アリスは食べる。", "As for Alice, eat.", "pre", "0.5", "1", "waiting", "アリスはたべる。"});
        preLoadedCards.add(new String[]{"ジムが遊ぶ。", "Jim is the one that plays.", "pre", "0.5", "1", "waiting", "ジムはあそぶ。"});
        preLoadedCards.add(new String[]{"ボブもする。", "Bob also do.", "pre", "0.5", "1", "waiting", "ボブもする。"});
        preLoadedCards.add(new String[]{"お金がある。", "There is money. (lit: As for money, it exists.)", "pre", "0.5", "1", "waiting", "おかねがある。"});
        preLoadedCards.add(new String[]{"私は買う。", "As for me, buy.", "pre", "0.5", "1", "waiting", "わたしはかう。"});
        preLoadedCards.add(new String[]{"猫はいる。", "There is cat. (lit: As for cat, it exists.)", "pre", "0.5", "1", "waiting", "ねこはいる。"});
        preLoadedCards.add(new String[]{"アリスは食べない。", "As for Alice, does not eat.", "pre", "0.5", "1", "waiting", "アリスはたべない。"});
        preLoadedCards.add(new String[]{"ジムが遊ばない。", "Jim is the one that does not play.", "pre", "0.5", "1", "waiting", "ジムはあそばない。"});
        preLoadedCards.add(new String[]{"ボブもしない。", "Bob also does not do.", "pre", "0.5", "1", "waiting", "ボブもしない。"});
        preLoadedCards.add(new String[]{"お金がない。", "There is no money. (lit: As for money, does not exist.)", "pre", "0.5", "1", "waiting", "おかねがない。"});
        preLoadedCards.add(new String[]{"私は買わない。", "As for me, not buy.", "pre", "0.5", "1", "waiting", "わたしはかわない。"});
        preLoadedCards.add(new String[]{"猫はいない。", "There is no cat. (lit: As for cat, does not exist.)", "pre", "0.5", "1", "waiting", "ねこはいない。"});
        preLoadedCards.add(new String[]{"ご飯は、食べた。", "As for meal, ate.", "pre", "0.5", "1", "waiting", "ごはんは、たべた。"});
        preLoadedCards.add(new String[]{"映画は、全部見た。", "As for movie, saw them all.", "pre", "0.5", "1", "waiting", "えいがは、ぜんぶみた。"});
        preLoadedCards.add(new String[]{"今日は、走った。", "As for today, ran.", "pre", "0.5", "1", "waiting", "きょうは、はしった。"});
        preLoadedCards.add(new String[]{"友達が来た。", "Friend is the one that came.", "pre", "0.5", "1", "waiting", "ともだちがきた。"});
        preLoadedCards.add(new String[]{"私も遊んだ。", "I also played.", "pre", "0.5", "1", "waiting", "わたしもあそんだ。"});
        preLoadedCards.add(new String[]{"勉強は、した。", "About homework, did it.", "pre", "0.5", "1", "waiting", "べんきょうは、した。"});
        preLoadedCards.add(new String[]{"アリスは食べなかった。", "As for Alice, did not eat.", "pre", "0.5", "1", "waiting", "アリスはたべなかった。"});
        preLoadedCards.add(new String[]{"ジムがしなかった。", "Jim is the one that did not do.", "pre", "0.5", "1", "waiting", "ジムはしなかった。"});
        preLoadedCards.add(new String[]{"ボブも行かなかった。", "Bob also did not go.", "pre", "0.5", "1", "waiting", "ボブもいかなかった。"});
        preLoadedCards.add(new String[]{"お金がなかった。", "There was no money. (lit: As for money, did not exist.)", "pre", "0.5", "1", "waiting", "おかねがなかった。"});
        preLoadedCards.add(new String[]{"私は買わなかった。", "As for me, did not buy.", "pre", "0.5", "1", "waiting", "わたしはかわなかった。"});
        preLoadedCards.add(new String[]{"猫はいなかった。", "There was no cat. (lit: As for cat, did not exist.)", "pre", "0.5", "1", "waiting", "ねこはいなかった。"});
        preLoadedCards.add(new String[]{"魚を食べる。", "Eat fish.", "pre", "0.5", "1", "waiting", "さかなをたべる。"});
        preLoadedCards.add(new String[]{"ジュースを飲んだ。", "Drank juice.", "pre", "0.5", "1", "waiting", "ジュースをのんだ。"});
        preLoadedCards.add(new String[]{"街をぶらぶら歩く。", "Aimlessly walk through town. (Lit: Aimlessly walk town)", "pre", "0.5", "1", "waiting", "まちをぶらぶらあるく。"});
        preLoadedCards.add(new String[]{"高速道路を走る。", "Run through expressway. (Lit: Run expressway)", "pre", "0.5", "1", "waiting", "こうそくどうろをはしる。"});
        preLoadedCards.add(new String[]{"毎日、日本語を勉強する。", "Study Japanese everyday.", "pre", "0.5", "1", "waiting", "まいにち、にほんごをべんきょうする。"});
        preLoadedCards.add(new String[]{"メールアドレスを登録した。", "Registered email address.", "pre", "0.5", "1", "waiting", "メールアドレスをとうろくした。"});
        preLoadedCards.add(new String[]{"ボブは日本に行った。", "Bob went to Japan.", "pre", "0.5", "1", "waiting", "ボブはにほんにいった。"});
        preLoadedCards.add(new String[]{"家に帰らない。", "Not go back home.", "pre", "0.5", "1", "waiting", "{いえ|うち}にかえらない。"});
        preLoadedCards.add(new String[]{"部屋にくる。", "Come to room.", "pre", "0.5", "1", "waiting", "へやにくる。"});
        preLoadedCards.add(new String[]{"アリスは、アメリカからきた。", "Alice came from America.", "pre", "0.5", "1", "waiting", "アリスは、アメリカからきた。"});
        preLoadedCards.add(new String[]{"宿題を今日から明日までする。", "Will do homework from today to tomorrow.", "pre", "0.5", "1", "waiting", "しゅくだいをきょうからあしたまでする。"});
        preLoadedCards.add(new String[]{"猫は部屋にいる。", "Cat is in room.", "pre", "0.5", "1", "waiting", "ねこはへやにいる。"});
        preLoadedCards.add(new String[]{"椅子が台所にあった。", "Chair was in the kitchen.", "pre", "0.5", "1", "waiting", "いすがだいどころにあった。"});
        preLoadedCards.add(new String[]{"いい友達に会った。", "Met good friend.", "pre", "0.5", "1", "waiting", "いいともだちにあった。"});
        preLoadedCards.add(new String[]{"ジムは医者になる。", "Jim will become doctor.", "pre", "0.5", "1", "waiting", "ジムはいしゃになる。"});
        preLoadedCards.add(new String[]{"先週に図書館に行った。", "Went to library last week.", "pre", "0.5", "1", "waiting", "せんしゅうにとしょかんにいった。"});
        preLoadedCards.add(new String[]{"友達は、来年、日本に行く。", "Next year, friend go to Japan.", "pre", "0.5", "1", "waiting", "ともだちは、らいねん、にほんにいく。"});
        preLoadedCards.add(new String[]{"友達は、来年に日本に行く。", "Friend go to Japan next year.", "pre", "0.5", "1", "waiting", "ともだちは、らいねんににほんにいく。"});
        preLoadedCards.add(new String[]{"ボブは日本へ行った。", "Bob headed towards Japan.", "pre", "0.5", "1", "waiting", "ボブはにほんへいった。"});
        preLoadedCards.add(new String[]{"家へ帰らない。", "Not go home toward house.", "pre", "0.5", "1", "waiting", "{いえ|うち}へかえらない。"});
        preLoadedCards.add(new String[]{"部屋へくる。", "Come towards room.", "pre", "0.5", "1", "waiting", "へやへくる。"});
        preLoadedCards.add(new String[]{"勝ちへ向かう。", "Go towards victory.", "pre", "0.5", "1", "waiting", "かちへくかう。"});
        preLoadedCards.add(new String[]{"映画館で見た。", "Saw at movie theater.", "pre", "0.5", "1", "waiting", "えいがかんでみた。"});
        preLoadedCards.add(new String[]{"バスで帰る。", "Go home by bus.", "pre", "0.5", "1", "waiting", "バスでかえる。"});
        preLoadedCards.add(new String[]{"レストランで昼ご飯を食べた。", "Ate lunch at restaurant.", "pre", "0.5", "1", "waiting", "レストランでひるごはんをたべた。"});
        preLoadedCards.add(new String[]{"何できた？／バスできた。", "Came by the way of what? / Came by the way of bus.", "pre", "0.5", "1", "waiting", "なにできた？／バスできた。"});
        preLoadedCards.add(new String[]{"何できた？／暇だから。", "Why did you come? / Because I am free (as in have nothing to do).", "pre", "0.5", "1", "waiting", "なんできた？／ひまだから。"});
        preLoadedCards.add(new String[]{"学校に行った？／行かなかった。／図書館には？ ／図書館にも行かなかった。", "[Did you] go to school? / Didn't go. / What about library? / Also didn't go to library.", "pre", "0.5", "1", "waiting", "「がっこうにいった？／いかなかった。／としょかんは？／としょかんにもいかなかった。"});
        preLoadedCards.add(new String[]{"どこで食べる？ ／イタリアレストランではどう？", "Eat where? / How about Italian restaurant?", "pre", "0.5", "1", "waiting", "どこでだべる？／イタリアレストランではどう？"});
        preLoadedCards.add(new String[]{"日本語を習う。", "Learn Japanese.", "pre", "0.5", "1", "waiting", "にほんごをならう。"});
        preLoadedCards.add(new String[]{"日本語は、習う。", "About Japanese, (will) learn it.", "pre", "0.5", "1", "waiting", "にほんごは、ならう。"});
        preLoadedCards.add(new String[]{"私が電気を付けた。", "I am the one that turned on the lights.", "pre", "0.5", "1", "waiting", "わたしがでんきをつけた。"});
        preLoadedCards.add(new String[]{"電気が付いた。", "The lights turned on.", "pre", "0.5", "1", "waiting", "でんきがついた。"});
        preLoadedCards.add(new String[]{"電気を消す。", "Turn off the lights.", "pre", "0.5", "1", "waiting", "でんきをけす。"});
        preLoadedCards.add(new String[]{"電気が消える。", "Lights turn off.", "pre", "0.5", "1", "waiting", "でんきがきえる。"});
        preLoadedCards.add(new String[]{"誰が窓を開けた？", "Who opened the window?", "pre", "0.5", "1", "waiting", "だれがまどをあけた？"});
        preLoadedCards.add(new String[]{"窓がどうして開いた？", "Why has the window opened?", "pre", "0.5", "1", "waiting", "まどがどうしてあいた？"});
        preLoadedCards.add(new String[]{"部屋を出た。", "I left room.", "pre", "0.5", "1", "waiting", "へやをでた。"});
        preLoadedCards.add(new String[]{"学生じゃない人は、学校に行かない。", "Person who is not student do not go to school.", "pre", "0.5", "1", "waiting", "がくせいじゃないひとは、がっこうにいかない。"});
        preLoadedCards.add(new String[]{"子供だったアリスが立派な大人になった。", "The Alice that was a child became a fine adult.", "pre", "0.5", "1", "waiting", "こどもだったアリスがりっぱなおとなになった。"});
        preLoadedCards.add(new String[]{"友達じゃなかったアリスは、いい友達になった。", "Alice who was not a friend, became a good friend.", "pre", "0.5", "1", "waiting", "ともだちじゃなかったアリスは、いいともだちになった。"});
        preLoadedCards.add(new String[]{"先週に医者だったボブは、仕事を辞めた。", "Bob who was a doctor last week quit his job.", "pre", "0.5", "1", "waiting", "せんしゅうにいしゃだったボブは、しごとをやめた。"});
        preLoadedCards.add(new String[]{"先週に映画を見た人は誰？", "Who is person who watched movie last week?", "pre", "0.5", "1", "waiting", "せんしゅうにえいがをみたひとはだれ？"});
        preLoadedCards.add(new String[]{"ボブは、いつも勉強する人だ。", "Bob is a person who always studies.", "pre", "0.5", "1", "waiting", "ボブは、いつもべんきょうするひとだ。"});
        preLoadedCards.add(new String[]{"赤いズボンを買う友達はボブだ。", "Friend who buy red pants is Bob.", "pre", "0.5", "1", "waiting", "あかいボンをかうともだちはボブだ。"});
        preLoadedCards.add(new String[]{"晩ご飯を食べなかった人は、映画で見た銀行に行った。", "Person who did not eat dinner went to the bank she saw at movie.", "pre", "0.5", "1", "waiting", "ばんごはんをたべなかったひとは、えいがでみたぎんこうにいった。"});
        preLoadedCards.add(new String[]{"スプーンとフォークで魚を食べた。", "Ate fish by means of fork and spoon.", "pre", "0.5", "1", "waiting", "スプーンとフォークでさかなをたべた。"});
        preLoadedCards.add(new String[]{"本と雑誌と葉書を買った。", "Bought book, magazine, and post card.", "pre", "0.5", "1", "waiting", "ほんとざっしとはがきをかった。"});
        preLoadedCards.add(new String[]{"友達と話した。", "Talked with friend.", "pre", "0.5", "1", "waiting", "ともだちとはなした。"});
        preLoadedCards.add(new String[]{"先生と会った。", "Met with teacher.", "pre", "0.5", "1", "waiting", "せんせいとあった。"});
        preLoadedCards.add(new String[]{"飲み物やカップやナプキンは、いらない？", "You don't need (things like) drink, cup, or napkin, etc.?", "pre", "0.5", "1", "waiting", "のみものやカップやナプキンは、いらない？"});
        preLoadedCards.add(new String[]{"靴やシャツを買う。", "Buy (things like) shoes and shirt, etc...", "pre", "0.5", "1", "waiting", "くつやシャツをかう。"});
        preLoadedCards.add(new String[]{"飲み物とかカップとかナプキンは、いらない？", "You don't need (things like) drink, cup, or napkin, etc.?", "pre", "0.5", "1", "waiting", "のみものとかカップとかナプキンは、いらない？"});
        preLoadedCards.add(new String[]{"靴とかシャツを買う。", "Buy (things like) shoes and shirt, etc...", "pre", "0.5", "1", "waiting", "くつとかシャツをかう。"});
        preLoadedCards.add(new String[]{"ボブの本。", "Book of Bob.", "pre", "0.5", "1", "waiting", "ボブのほん。"});
        preLoadedCards.add(new String[]{"ボブは、アメリカの大学の学生だ。", "Bob is student of college of America.", "pre", "0.5", "1", "waiting", "ボブは、アメリカのだいがくのがくせいだ。"});
        preLoadedCards.add(new String[]{"そのシャツは誰の？／ボブのだ。", "Whose shirt is that? / It is of Bob.", "pre", "0.5", "1", "waiting", "そのシャツはだれの?／ボブのだ。"});
        preLoadedCards.add(new String[]{"白いのは、かわいい。", "Thing that is white is cute.", "pre", "0.5", "1", "waiting", "しろいのは、かわいい。"});
        preLoadedCards.add(new String[]{"授業に行くのを忘れた。", "Forgot the event of going to class.", "pre", "0.5", "1", "waiting", "じゅぎょうにいくのをわすれた。"});
        preLoadedCards.add(new String[]{"白い物は、かわいい。", "Thing that is white is cute.", "pre", "0.5", "1", "waiting", "しろいものは、かわいい。"});
        preLoadedCards.add(new String[]{"授業に行くことを忘れた。", "Forgot the thing of going to class.", "pre", "0.5", "1", "waiting", "じゅぎょうにいくことをわすれた。"});
        preLoadedCards.add(new String[]{"毎日勉強するのは大変。", "The thing of studying every day is tough.", "pre", "0.5", "1", "waiting", "まいにちべんきょうのはたいへん。"});
        preLoadedCards.add(new String[]{"毎日同じ物を食べるのは、面白くない。", "It's not interesting to eat same thing every day.", "pre", "0.5", "1", "waiting", "まいにちおなじものをたべるのは、おもしろくない。"});
        preLoadedCards.add(new String[]{"静かなのが、アリスの部屋だ。", "Quiet one is room of Alice.", "pre", "0.5", "1", "waiting", "しずかなのが、アリスのへやだ。"});
        preLoadedCards.add(new String[]{"今は忙しいの。", "The thing is that (I'm) busy now.", "pre", "0.5", "1", "waiting", "いまはいそがしいの。"});
        preLoadedCards.add(new String[]{"今は忙しいのだ。", "The thing is that (I'm) busy now.", "pre", "0.5", "1", "waiting", "いまはいそがしいのだ。"});
        preLoadedCards.add(new String[]{"今は忙しいの？", "Is it that (you) are busy now? (gender-neutral)", "pre", "0.5", "1", "waiting", "いまはいそがしいの？"});
        preLoadedCards.add(new String[]{"ジムのだ。", "It is of Jim. (It is Jim's.)", "pre", "0.5", "1", "waiting", "ジムのだ。"});
        preLoadedCards.add(new String[]{"ジムなのだ。", "It is Jim (with explanatory tone).", "pre", "0.5", "1", "waiting", "ジムなのだ。"});
        preLoadedCards.add(new String[]{"どこに行くの？／授業に行くんだ。", "Where is it that (you) are going? / It is that (I) go to class.", "pre", "0.5", "1", "waiting", "どこにいくの？／じゅぎょうにいくんだ。"});
        preLoadedCards.add(new String[]{"今、授業があるんじゃない？／今は、ないんだ。", "Isn't it that there is class now? / Now it is that there is no class.", "pre", "0.5", "1", "waiting", "いま、じゅぎょうがあるんじゃない？／いまは、ないんだ。"});
        preLoadedCards.add(new String[]{"今、授業がないんじゃない？／ううん、ある。", "Isn't it that there isn't class now? / No, there is.", "pre", "0.5", "1", "waiting", "いま、じゅぎょうがないんじゃない？／ううん、ある。"});
        preLoadedCards.add(new String[]{"その人が買うんじゃなかったの？／ううん、先生が買うんだ。", "Wasn't it that that person was the one to buy? / No, it is that teacher is the one to buy.", "pre", "0.5", "1", "waiting", "そのひとがかうんじゃなかったの？／ううん、せんせいがかうんだ。"});
        preLoadedCards.add(new String[]{"朝ご飯を食べるんじゃなかった。／どうして？", "It is that breakfast wasn't to eat. / Why?", "pre", "0.5", "1", "waiting", "あさごはんをたべるんじゃなかった。／どうして？"});
        preLoadedCards.add(new String[]{"ボブは朝ご飯を早く食べた。", "Bob quickly ate breakfast.", "pre", "0.5", "1", "waiting", "ボブはあさごはんをはやくたべた。"});
        preLoadedCards.add(new String[]{"アリスは自分の部屋をきれいにした。", "Alice did her own room toward clean.", "pre", "0.5", "1", "waiting", "アリスはじぶんのへやをきれいにした。"});
        preLoadedCards.add(new String[]{"映画をたくさん見た。", "Saw a lot of movies.", "pre", "0.5", "1", "waiting", "えいがをたくさんみた。"});
        preLoadedCards.add(new String[]{"最近、全然食べない。", "Lately, don't eat at all.", "pre", "0.5", "1", "waiting", "さいきん、ぜんぜんたべない。"});
        preLoadedCards.add(new String[]{"ボブの声は、結構大きい。", "Bob's voice is fairly large.", "pre", "0.5", "1", "waiting", "ボブのこえは、けっこうおおきい。"});
        preLoadedCards.add(new String[]{"この町は、最近大きく変わった。", "This town had changed greatly lately.", "pre", "0.5", "1", "waiting", "このまちほ、さいきんおおきくかわった。"});
        preLoadedCards.add(new String[]{"図書館の中では、静かにする。", "Within the library, [we] do things quietly.", "pre", "0.5", "1", "waiting", "としょかんのなかでは、しずかにする。"});
        preLoadedCards.add(new String[]{"いい天気だね。／そうね。", "Good weather, huh? / That is so, isn't it?", "pre", "0.5", "1", "waiting", "いいてんきだね。／そうね。"});
        preLoadedCards.add(new String[]{"おもしろい映画だったね。／え？全然おもしろくなかった。", "That was interesting movie, wasn't it? / Huh? No, it wasn't interesting at all.", "pre", "0.5", "1", "waiting", "おもしろいえいがだったね。／え？ぜんぜんおもしろくなかった。"});
        preLoadedCards.add(new String[]{"時間がないよ。／大丈夫だよ。", "You know, there is no time. / It's ok, you know.", "pre", "0.5", "1", "waiting", "じかんがないよ。／だいじょうぶだよ。"});
        preLoadedCards.add(new String[]{"今日はいい天気だね。／うん。でも、明日雨が降るよ。", "Good weather today, huh? / Yeah. But it will rain tomorrow, you know.", "pre", "0.5", "1", "waiting", "きょうはいいてんきだね。／うん。でも、あしたあめがふるよ。"});
        preLoadedCards.add(new String[]{"ボブは、魚が好きなんだよね。／そうだね。", "You know, you like fish, dontcha? / That is so, huh?", "pre", "0.5", "1", "waiting", "ボブは、さかながすきなんだよね。／そうだね。"});
        preLoadedCards.add(new String[]{"明日、映画を見に行く。", "Tomorrow, go to see movie.", "pre", "0.5", "1", "waiting", "あした、えいがをみにいく。"});
        preLoadedCards.add(new String[]{"昨日、友達が遊びにきた。", "Yesterday, friend came to play.", "pre", "0.5", "1", "waiting", "きのう、ともだちがあそびにきた。"});
        preLoadedCards.add(new String[]{"明日、大学に行きます。", "Tomorrow, go to college.", "pre", "0.5", "1", "waiting", "あした、だいがくにいきます。"});
        preLoadedCards.add(new String[]{"先週、ボブに会いましたよ。", "You know, met Bob last week.", "pre", "0.5", "1", "waiting", "せんしゅう、ボブにあいましたよ。"});
        preLoadedCards.add(new String[]{"晩ご飯を食べませんでしたね。", "Didn't eat dinner, huh?", "pre", "0.5", "1", "waiting", "ばんごはんをたべませんでしたね。"});
        preLoadedCards.add(new String[]{"面白くない映画は見ません。", "About not interesting movies, do not see (them).", "pre", "0.5", "1", "waiting", "おもしろくないえいがほみません。"});
        preLoadedCards.add(new String[]{"子犬はとても好きです。", "About puppies, like very much.", "pre", "0.5", "1", "waiting", "こいぬはとてもすきです。"});
        preLoadedCards.add(new String[]{"昨日、時間がなかったんです。", "It was that there was no time yesterday.", "pre", "0.5", "1", "waiting", "きのう、じかんがなかったんです。"});
        preLoadedCards.add(new String[]{"その部屋はあまり静かじゃないです。", "That room is not very quiet.", "pre", "0.5", "1", "waiting", "そのへやはあまりしずかじゃないです。"});
        preLoadedCards.add(new String[]{"先週に見た映画は、とても面白かったです。", "Movie saw last week was very interesting.", "pre", "0.5", "1", "waiting", "せんしゅうにみたえいがは、とてもおもしろかったです。"});
        preLoadedCards.add(new String[]{"その部屋はあまり静かじゃないですよ。", "You know, that room is not very quiet.", "pre", "0.5", "1", "waiting", "そのへやはあまりしずかじゃないですよ。"});
        preLoadedCards.add(new String[]{"その部屋はあまり静かじゃありませんよ。", "You know, that room is not very quiet.", "pre", "0.5", "1", "waiting", "そのへやはあまりしずかじゃありませんよ。"});
        preLoadedCards.add(new String[]{"私の名前はキムです。", "My name is Kim. (Neutral, polite)", "pre", "0.5", "1", "waiting", "わたしのなまえはキムです。"});
        preLoadedCards.add(new String[]{"僕の名前はキムです。", "My name is Kim. (Masculine, polite)", "pre", "0.5", "1", "waiting", "ぼくのなまえはキムです。"});
        preLoadedCards.add(new String[]{"僕の名前はボブだ。", "My name is Bob. (Masculine, casual)", "pre", "0.5", "1", "waiting", "ぼくのなまえはボブだ。"});
        preLoadedCards.add(new String[]{"俺の名前はボブだ。", "My name is Bob. (Masculine, casual)", "pre", "0.5", "1", "waiting", "おれのなまえはボブだ。"});
        preLoadedCards.add(new String[]{"あたしの名前はアリス。", "My name is Alice. (Feminine, casual)", "pre", "0.5", "1", "waiting", "あたしのなまえはアリス。"});
        preLoadedCards.add(new String[]{"お母さんはどこですか。母は買い物に行きました。", "Where is (your) mother? / (My) mother went shopping.", "pre", "0.5", "1", "waiting", "おかあさんはどこですか。はははかいものにいきますた。"});
        preLoadedCards.add(new String[]{"イタリア料理を食べに行きませんか。／すみません。ちょっと、お腹がいっぱいです。", "Go to eat Italian food?Sorry. / (My) stomach is a little full.", "pre", "0.5", "1", "waiting", "イタリアりょうりをたべにいきませんか。／すみません。ちょっと、おなかがいっぱいです。"});
        preLoadedCards.add(new String[]{"こんなのを本当に食べるか？", "Do you think [he/she] will really eat this type of thing?", "pre", "0.5", "1", "waiting", "こんなのをほんとうにたべるか？"});
        preLoadedCards.add(new String[]{"そんなのは、あるかよ！", "Do I look like I would have something like that?!", "pre", "0.5", "1", "waiting", "そんなのは、あるかよ！"});
        preLoadedCards.add(new String[]{"こんなのを本当に食べる？", "Are you really going to eat something like this?", "pre", "0.5", "1", "waiting", "こんなのをほんとうにたべる？"});
        preLoadedCards.add(new String[]{"そんなのは、あるの？", "Do you have something like that?", "pre", "0.5", "1", "waiting", "そんなのは、あるの？"});
        preLoadedCards.add(new String[]{"昨日何を食べたか忘れた。", "Forgot what I ate yesterday.", "pre", "0.5", "1", "waiting", "きのうなにをたべたかわすれた。"});
        preLoadedCards.add(new String[]{"彼は何を言ったかわからない。", "Don't understand what he said.", "pre", "0.5", "1", "waiting", "かれはなにをいったかわからない。"});
        preLoadedCards.add(new String[]{"先生が学校に行ったか教えない？", "Won't you inform me whether teacher went to school?", "pre", "0.5", "1", "waiting", "せんせいががっこうにいったかおしえない？"});
        preLoadedCards.add(new String[]{"先生が学校に行ったかどうか知らない。", "Don't know whether or not teacher went to school.", "pre", "0.5", "1", "waiting", "せんせいががっこうにいったかどうかしらない。"});
        preLoadedCards.add(new String[]{"先生が学校に行ったか行かなかったか知らない。", "Don't know whether teacher went to school or didn't.", "pre", "0.5", "1", "waiting", "せんせいががっこうにいったかいかなかったかしらない。"});
        preLoadedCards.add(new String[]{"誰かがおいしいクッキーを全部食べた。", "Someone ate all the delicious cookies.", "pre", "0.5", "1", "waiting", "だれかがおいしいクッキーをぜんぶたべた。"});
        preLoadedCards.add(new String[]{"誰が盗んだのか、誰か知りませんか。", "Does anybody know who stole it?", "pre", "0.5", "1", "waiting", "だれがぬすんだのか、だれかしりませんか。"});
        preLoadedCards.add(new String[]{"犯人をどこかで見ましたか。", "Did you see the criminal somewhere?", "pre", "0.5", "1", "waiting", "はんにんをどこかでみましたか。"});
        preLoadedCards.add(new String[]{"この中からどれかを選ぶの。", "(Explaining) You are to select a certain one from inside this [selection].", "pre", "0.5", "1", "waiting", "このなかからどれかをえらぶの。"});
        preLoadedCards.add(new String[]{"この質問の答えは、誰も知らない。", "Nobody knows the answer of this question.", "pre", "0.5", "1", "waiting", "このしつもんのこたえは、だれもしらない。"});
        preLoadedCards.add(new String[]{"友達はいつも遅れる。", "Friend is always late.", "pre", "0.5", "1", "waiting", "ともだちはいつもおくれる。"});
        preLoadedCards.add(new String[]{"ここにあるレストランはどれもおいしくない。", "Any and all restaurants that are here are not tasty.", "pre", "0.5", "1", "waiting", "ここにあるレストランはどれもおいしくない。"});
        preLoadedCards.add(new String[]{"今週末は、どこにも行かなかった。", "Went nowhere this weekend.", "pre", "0.5", "1", "waiting", "こんしゅうまつは、どこにもいかなかった。"});
        preLoadedCards.add(new String[]{"この質問の答えは、誰でも分かる。", "Anybody understands the answer of this question.", "pre", "0.5", "1", "waiting", "このしつもんのこたえは、だれでもわかる。"});
        preLoadedCards.add(new String[]{"昼ご飯は、どこでもいいです。", "About lunch, anywhere is good.", "pre", "0.5", "1", "waiting", "ひるごはんは、どこでもいいです。"});
        preLoadedCards.add(new String[]{"あの人は、本当に何でも食べる。", "That person really eats anything.", "pre", "0.5", "1", "waiting", "あのひとは、ほんとうになんでもたべる。"});
        preLoadedCards.add(new String[]{"私の部屋は、きれいで、静かで、とても好き。", "My room is clean, quiet, and I like it a lot.", "pre", "0.5", "1", "waiting", "わたしのへやは、きれいで、しずかで、とてもすき。"});
        preLoadedCards.add(new String[]{"彼女は、学生じゃなくて、先生だ。", "She is not a student, she is a teacher.", "pre", "0.5", "1", "waiting", "彼女は、がくせいじゃなくて、せんせいだ。"});
        preLoadedCards.add(new String[]{"田中さんは、お金持ちで、かっこよくて、魅力的ですね。", "Tanaka-san is rich, handsome, and charming, isn't he?", "pre", "0.5", "1", "waiting", "たなかさんは、おかねもちで、かっこよくて、みりょくてきですね。"});
        preLoadedCards.add(new String[]{"食堂に行って、昼ご飯を食べて、昼寝をする。", "I will go to cafeteria, eat lunch, and take a nap.", "pre", "0.5", "1", "waiting", "しょくどうにいって、ひるごはんをたべて、ひるねをする。"});
        preLoadedCards.add(new String[]{"食堂に行って、昼ご飯を食べて、昼寝をした。", "I went to cafeteria, ate lunch, and took a nap.", "pre", "0.5", "1", "waiting", "しょくどうにいって、ひるごはんをたべて、ひるねをした。"});
        preLoadedCards.add(new String[]{"時間がありまして、映画を見ました。", "There was time and I watched a movie.", "pre", "0.5", "1", "waiting", "じかんがありまして、えいがをみました。"});
        preLoadedCards.add(new String[]{"時間がなかったからパーティーに行きませんでした。", "There was no time so didn't go to party.", "pre", "0.5", "1", "waiting", "じかんがなかったからパーティーにいきませんでした。"});
        preLoadedCards.add(new String[]{"友達からプレゼントが来た。", "Present came from friend.", "pre", "0.5", "1", "waiting", "ともだちからプレゼントがきた。"});
        preLoadedCards.add(new String[]{"友達だからプレゼントが来た。", "Present came because (the person is) friend. (This sentence sounds a bit odd.)", "pre", "0.5", "1", "waiting", "ともだちだからプレゼントがきた。"});
        preLoadedCards.add(new String[]{"どうしてパーティーに行きませんでしたか。／時間がなかったからです。", "Why didn't you go to the party? / It's because I didn't have time.", "pre", "0.5", "1", "waiting", "どうしてパーティーにいきませんでしたか。／じかんがなかったからです。"});
        preLoadedCards.add(new String[]{"パーティーに行かなかったの？／うん、時間がなかったから。", "You didn't go to the party? / Yeah, because I didn't have time.", "pre", "0.5", "1", "waiting", "パーティーにいかなかったの？／うん、じかんがなかったから。"});
        preLoadedCards.add(new String[]{"時間がなかった。／だからパーティーに行かなかったの？", "I didn't have time. / Is that why you didn't go to the party.", "pre", "0.5", "1", "waiting", "じかんがなかった。／だからパーティーにいかなかったの？"});
        preLoadedCards.add(new String[]{"ちょっと忙しいので、そろそろ失礼します。", "Because I'm a little busy, I'll be making my leave soon.", "pre", "0.5", "1", "waiting", "ちょっといそがしいので、そろそろしつれいします。"});
        preLoadedCards.add(new String[]{"私は学生なので、お金がないんです。", "Because I'm a student, I have no money (lit: there is no money).", "pre", "0.5", "1", "waiting", "わたしはがくせいなので、おかながないんです。"});
        preLoadedCards.add(new String[]{"ここは静かなので、とても穏やかです。", "It is very calm here because it is quiet.", "pre", "0.5", "1", "waiting", "ここはすずかなので、とてもおだやかです。"});
        preLoadedCards.add(new String[]{"なので、友達に会う時間がない。", "That's why there's no time to meet friend.", "pre", "0.5", "1", "waiting", "なので、ともだちにあうじかんがない。"});
        preLoadedCards.add(new String[]{"時間がなかったんでパーティーに行かなかった。", "Didn't go to the party because there was no time.", "pre", "0.5", "1", "waiting", "じかんがなかったんでパーティーにいかなかった。"});
        preLoadedCards.add(new String[]{"ここは静かなんで、とても穏やかです。", "It is very calm here because it is quiet.", "pre", "0.5", "1", "waiting", "ここはしずかなんで、とてもおだやかです。"});
        preLoadedCards.add(new String[]{"なんで、友達に会う時間がない。", "That's why there's no time to meet friend.", "pre", "0.5", "1", "waiting", "なんで、ともだちにあうじかんがない。"});
        preLoadedCards.add(new String[]{"毎日運動したのに、全然痩せなかった。", "Despite exercising every day, I didn't get thinner.", "pre", "0.5", "1", "waiting", "まいにちうんどうしたのに、ぜんぜんやせなかった。"});
        preLoadedCards.add(new String[]{"学生なのに、彼女は勉強しない。", "Despite being a student, she does not study.", "pre", "0.5", "1", "waiting", "がくせいなのに、かのじょはべんきょうしない。"});
        preLoadedCards.add(new String[]{"デパートに行きましたが、何も欲しくなかったです。", "I went to department store but there was nothing I wanted.", "pre", "0.5", "1", "waiting", "デパートにいきましたが、なにもほしくなかったです。"});
        preLoadedCards.add(new String[]{"友達に聞いたけど、知らなかった。", "I asked (or heard from) a friend but he (or I) didn't know.", "pre", "0.5", "1", "waiting", "ともだちにきいたけど、しらなかった。"});
        preLoadedCards.add(new String[]{"今日は暇だけど、明日は忙しい。", "I'm free today but I will be busy tomorrow.", "pre", "0.5", "1", "waiting", "きょうはひまだけど、あしたはいそがしい。"});
        preLoadedCards.add(new String[]{"だけど、彼がまだ好きなの。", "That may be so, but it is that I still like him. (explanation, feminine tone)", "pre", "0.5", "1", "waiting", "だけど、かれがまだすきなの。"});
        preLoadedCards.add(new String[]{"デパートに行きましたが、いい物がたくさんありました。", "I went to the department store and there was a lot of good stuff.", "pre", "0.5", "1", "waiting", "デパートにいきましたが、いいものがたくさんありました。"});
        preLoadedCards.add(new String[]{"マトリックスを見たけど、面白かった。", "I watched the Matrix and it was interesting.", "pre", "0.5", "1", "waiting", "マトリックスをみたけど、おもしろかった。"});
        preLoadedCards.add(new String[]{"どうして友達じゃないんですか？／先生だし、年上だし・・・。", "Why isn't him/her friend (seeking explanation)? / Well, he's/she's the teacher, and older...", "pre", "0.5", "1", "waiting", "どうしてともだちじゃないんですか？／せんせいだし、ねんうえだし・・・。"});
        preLoadedCards.add(new String[]{"どうして彼が好きなの？／優しいし、かっこいいし、面白いから。", "Why (do you) like him? / Because he's kind, attractive, and interesting (among other things).", "pre", "0.5", "1", "waiting", "どうしてかれがすきなの？／やさしいし、かっこいいし、おもしろいから。"});
        preLoadedCards.add(new String[]{"映画を見たり、本を読んだり、昼寝したりする。", "I do things like (among other things) watch movies, read books, and take naps.", "pre", "0.5", "1", "waiting", "えいがをみたり、ほんをやんだり、ひるねしたりする。"});
        preLoadedCards.add(new String[]{"この大学の授業は簡単だったり、難しかったりする。", "Class of this college is sometimes easy, sometimes difficult (and other times something else maybe).", "pre", "0.5", "1", "waiting", "このだいがくのじゅぎょうはかんたんだったり、むずかしかったりする。"});
        preLoadedCards.add(new String[]{"映画を見たり、本を読んだりした。", "I did things like (among other things) watch movies, and read books.", "pre", "0.5", "1", "waiting", "えいがをみたり、ほんをよんだりした。"});
        preLoadedCards.add(new String[]{"映画を見たり、本を読んだりしない。", "I don't do things like (among other things) watch movies, and read books.", "pre", "0.5", "1", "waiting", "えいがをみたり、ほんをよんだりしない。"});
        preLoadedCards.add(new String[]{"映画を見たり、本を読んだりしなかった。", "I didn't do things like (among other things) watch movies, and read books.", "pre", "0.5", "1", "waiting", "えいがをみたり、ほんをよんだりしなかった。"});
        preLoadedCards.add(new String[]{"友達は何をしているの？／昼ご飯を食べている。", "What is friend doing? / (Friend) is eating lunch.", "pre", "0.5", "1", "waiting", "ともだちはなにをしているの？／ひるごはんをたべている。"});
        preLoadedCards.add(new String[]{"何を読んでいる？／教科書を読んでいます。", "What are you reading? / I am reading textbook.", "pre", "0.5", "1", "waiting", "なにをよんでいる？／きょうかしょをよんでいます。"});
        preLoadedCards.add(new String[]{"話を聞いていますか。／ううん、聞いていない。", "Are you listening to me? (lit: Are you listening to story?) / No, I'm not listening.", "pre", "0.5", "1", "waiting", "はなしをきいていますか。／ううん、きいていない。"});
        preLoadedCards.add(new String[]{"友達は何をしてるの？／昼ご飯を食べてる。", "What is friend doing? / (Friend) is eating lunch.", "pre", "0.5", "1", "waiting", "ともだちはなにをしてるの？／ひるごはんをたべている。"});
        preLoadedCards.add(new String[]{"何を読んでる？／教科書を読んでいます。", "What are you reading? / I am reading textbook.", "pre", "0.5", "1", "waiting", "なにをよんでる？／きょうかしょをよんでいます。"});
        preLoadedCards.add(new String[]{"話を聞いていますか。／ううん、聞いてない。", "Are you listening to me? (lit: Are you listening to story?) / No, I'm not listening.", "pre", "0.5", "1", "waiting", "はなしをきいていますか。／ううん、きいてない。"});
        preLoadedCards.add(new String[]{"今日、知りました。", "I found out about it today. (I did the action of knowing today.)", "pre", "0.5", "1", "waiting", "きょう、しりました。"});
        preLoadedCards.add(new String[]{"この歌を知っていますか？", "Do (you) know this song?", "pre", "0.5", "1", "waiting", "このうたをしっていますか。"});
        preLoadedCards.add(new String[]{"道は分かりますか。", "Do you know the way? (lit: Do (you) understand the road?)", "pre", "0.5", "1", "waiting", "みちはわかりますか。"});
        preLoadedCards.add(new String[]{"はい、はい、分かった、分かった。", "Yes, yes, I got it, I got it.", "pre", "0.5", "1", "waiting", "はい、はい、わかった、わかった。"});
        preLoadedCards.add(new String[]{"鈴木さんはどこですか。／もう、家に帰っている。", "Where is Suzuki-san? / He is already at home (went home and is there now).", "pre", "0.5", "1", "waiting", "すずきさんはどこですか。／もう{うち|えい}にかえっている。"});
        preLoadedCards.add(new String[]{"先に行っているよ。／美恵ちゃんは、もう来ているよ。", "I'll go on ahead. (I'll go and be there before you.) / Mie-chan is already here, you know. (She came and is here.)", "pre", "0.5", "1", "waiting", "さきにいっているよ。／みえちゃんは、もうきているよ。"});
        preLoadedCards.add(new String[]{"準備はどうですか。準備は、もうしてあるよ。", "How are the preparations? / The preparations are already done.", "pre", "0.5", "1", "waiting", "じゅんびはどうですか。／じゅんびは、もうしてあるよ。"});
        preLoadedCards.add(new String[]{"旅行の計画は終った？／うん、切符を買ったし、ホテルの予約もしてある。", "Are the plans for the trip complete? / Uh huh, not only did I buy the ticket, I also took care of the hotel reservations.", "pre", "0.5", "1", "waiting", "りょこうのけいかくはおわった？／うん、きっぷをかったし、ホテルのよやくもしてある。"});
        preLoadedCards.add(new String[]{"晩ご飯を作っておく。", "Make dinner (in advance for the future).", "pre", "0.5", "1", "waiting", "ばんごはんをつくっておく。"});
        preLoadedCards.add(new String[]{"電池を買っておきます。", "I'll buy batteries (in advance for the future).", "pre", "0.5", "1", "waiting", "でんちをかっておきます。"});
        preLoadedCards.add(new String[]{"晩ご飯を作っとく。", "Make dinner (in advance for the future).", "pre", "0.5", "1", "waiting", "ばんごはんをつくっとく。"});
        preLoadedCards.add(new String[]{"電池を買っときます。", "I'll buy batteries (in advance for the future).", "pre", "0.5", "1", "waiting", "だんちをかっときます。"});
        preLoadedCards.add(new String[]{"鉛筆を持っている？", "Do (you) have a pencil?", "pre", "0.5", "1", "waiting", "えんぴつをもっている。"});
        preLoadedCards.add(new String[]{"鉛筆を学校へ持っていく？", "Are (you) taking pencil to school?", "pre", "0.5", "1", "waiting", "えんぴつをがっこうへもっていく？"});
        preLoadedCards.add(new String[]{"鉛筆を家に持ってくる？", "Are (you) bringing pencil to home?", "pre", "0.5", "1", "waiting", "えんぴつを{うち|いえ}にもってくる。"});
        preLoadedCards.add(new String[]{"お父さんは、早く帰ってきました。", "Father came back home early.", "pre", "0.5", "1", "waiting", "おとうさんは、はやくかえってきました。"});
        preLoadedCards.add(new String[]{"駅の方へ走っていった。", "Ran toward the direction of station.", "pre", "0.5", "1", "waiting", "えきのほうへはしっていった。"});
        preLoadedCards.add(new String[]{"冬に入って、コートを着ている人が増えていきます。", "Entering winter, people wearing coat will increase (toward the future).", "pre", "0.5", "1", "waiting", "ふゆにはいって、コートをきているひとがふえていきます。"});
        preLoadedCards.add(new String[]{"一生懸命、頑張っていく！", "Will try my hardest (toward the future) with all my might!", "pre", "0.5", "1", "waiting", "いっしょうけんめい、がんばっていく！"});
        preLoadedCards.add(new String[]{"色々な人と付き合ってきたけど、いい人はまだ見つからない。", "Went out (up to the present) with various types of people but have yet to find a good person.", "pre", "0.5", "1", "waiting", "いろいろなひととつきあってきたけど、いいひとはまだみつからない。"});
        preLoadedCards.add(new String[]{"日本語をずっと前から勉強してきて、結局はやめた。", "Studied Japanese from way back before and eventually quit.", "pre", "0.5", "1", "waiting", "にほんをずっとまえからべんきょうしてきて、けっきょくはやめた。"});
        preLoadedCards.add(new String[]{"漢字は書けますか？", "Can you write kanji?", "pre", "0.5", "1", "waiting", "かんじはかけますか。"});
        preLoadedCards.add(new String[]{"残念だが、今週末は行けない。", "It's unfortunate, but can't go this weekend.", "pre", "0.5", "1", "waiting", "ざんねんだが、こんしゅうまつはいけない。"});
        preLoadedCards.add(new String[]{"もう信じられない。", "I can't believe it already.", "pre", "0.5", "1", "waiting", "もうしんじられない。"});
        preLoadedCards.add(new String[]{"富士山が登れた。", "Was able to climb Fuji-san.", "pre", "0.5", "1", "waiting", "ふじさんがのぼれた。"});
        preLoadedCards.add(new String[]{"重い荷物が持てます。", "Am able to hold heavy baggage.", "pre", "0.5", "1", "waiting", "おもいにもつがもてます。"});
        preLoadedCards.add(new String[]{"今日は晴れて、富士山が見える。", "It cleared up today and Fuji-san is visible.", "pre", "0.5", "1", "waiting", "きょうははれて、ふじさんがみえる。"});
        preLoadedCards.add(new String[]{"友達のおかげで、映画はただで見られた。", "Thanks to [my] friend, [I] was able to watch the movie for free.", "pre", "0.5", "1", "waiting", "ともだちのあかげで、えいがはただでみられた。"});
        preLoadedCards.add(new String[]{"友達のおかげで、映画をただで見ることができた。", "Thanks to [my] friend, [I] was able to watch the movie for free.", "pre", "0.5", "1", "waiting", "ともだちのおかげで、えいがをただでみることができた。"});
        preLoadedCards.add(new String[]{"久しぶりに彼の声が聞けた。", "I was able to hear his voice for the first time in a long time.", "pre", "0.5", "1", "waiting", "ひさしぶりにかれのこえがきけた。"});
        preLoadedCards.add(new String[]{"周りがうるさくて、彼が言っていることがあんまり聞こえなかった。", "The surroundings were noisy and I couldn't hear what he was saying very well.", "pre", "0.5", "1", "waiting", "まわりがうるさくて、かれがいっていることがあんまりきこえなかった。"});
        preLoadedCards.add(new String[]{"そんなことはありうる。", "That kind of situation/event is possible (lit: can exist).", "pre", "0.5", "1", "waiting", "そんなことはありうる。"});
        preLoadedCards.add(new String[]{"そんなことはありえる", "That kind of situation/event is possible (lit: can exist).", "pre", "0.5", "1", "waiting", "そんなことはありえる"});
        preLoadedCards.add(new String[]{"そんなことはありえない。", "That kind of situation/event is not possible (lit: cannot exist).", "pre", "0.5", "1", "waiting", "そんなことはありえない。"});
        preLoadedCards.add(new String[]{"彼が寝坊したこともありうるね。", "It's also possible that he overslept. (lit: The event that he overslept also possibly exists.)", "pre", "0.5", "1", "waiting", "かれがねぼうしたこともありうるね。"});
        preLoadedCards.add(new String[]{"それは、ありえない話だよ。", "That's an impossible story/scenario. (lit: That story/scenario cannot exist.)", "pre", "0.5", "1", "waiting", "それは、ありえないはなしだよ。"});
        preLoadedCards.add(new String[]{"彼の日本語が上手になった。", "His Japanese has become skillful.", "pre", "0.5", "1", "waiting", "かれのにほんががじょうずになった。"});
        preLoadedCards.add(new String[]{"私は医者になった。", "I became a doctor.", "pre", "0.5", "1", "waiting", "わたしはいしゃになった。"});
        preLoadedCards.add(new String[]{"私は有名な人になる。", "I will become a famous person.", "pre", "0.5", "1", "waiting", "わたしはゆうめいなひとになる。"});
        preLoadedCards.add(new String[]{"私は、ハンバーガーとサラダにします。", "I'll have the hamburger and salad. (lit: I'll do toward hamburger and salad.)", "pre", "0.5", "1", "waiting", "わたしは、ハンバーガーとサラダにします。"});
        preLoadedCards.add(new String[]{"他にいいものがたくさんあるけど、やっぱりこれにする。", "There are a lot of other good things, but as I thought, I'll go with this one.", "pre", "0.5", "1", "waiting", "ほかにいいものがたくさんあるけど、やっぱりこれにする。"});
        preLoadedCards.add(new String[]{"去年から背が高くなったね。", "Your height has gotten taller from last year, huh?", "pre", "0.5", "1", "waiting", "きょねんからせがたかくなったね。"});
        preLoadedCards.add(new String[]{"運動しているから、強くなる。", "I will become stronger because I am exercising.", "pre", "0.5", "1", "waiting", "うんどうしているから、つよくなる。"});
        preLoadedCards.add(new String[]{"勉強をたくさんしたから、頭がよくなった。", "Since I studied a lot, I became smarter. (lit: head became better)", "pre", "0.5", "1", "waiting", "べんきょうをたくさんしたから、あたまがよくなった。"});
        preLoadedCards.add(new String[]{"海外に行くことになった。", "It's been decided that I will go abroad. (lit: It became the event of going abroad.)", "pre", "0.5", "1", "waiting", "かいがいにいくことになった。"});
        preLoadedCards.add(new String[]{"毎日、肉を食べるようになった。", "It seems like I started eating meat everyday. (lit: It became the appearance of eating meat everyday.", "pre", "0.5", "1", "waiting", "まいにち、にくをたべるようになった。"});
        preLoadedCards.add(new String[]{"海外に行くことにした。", "I decided I will go abroad. (lit: I did toward the event of going abroad.)", "pre", "0.5", "1", "waiting", "かいがいにいくことにした。"});
        preLoadedCards.add(new String[]{"毎日、肉を食べるようにする。", "I will try to eat meat everyday. (lit: I will do toward the manner of eating meat everyday.)", "pre", "0.5", "1", "waiting", "まいにち、にくをたべるようにする。"});
        preLoadedCards.add(new String[]{"日本に来て、寿司が食べられるようになった。", "After coming to Japan, I became able to eat sushi.", "pre", "0.5", "1", "waiting", "にほんにきて、すしがたべられるようになった。"});
        preLoadedCards.add(new String[]{"一年間練習したから、ピアノが弾けるようになった。", "Because I practiced for one year, I became able to play the piano.", "pre", "0.5", "1", "waiting", "いちねんかんれんしゅうしたから、ピアノがひけるようになった。"});
        preLoadedCards.add(new String[]{"地下に入って、富士山が見えなくなった。", "After going underground, Fuji-san became not visible.", "pre", "0.5", "1", "waiting", "ちかにはいって、ふじさんがみえなくなった。"});
        preLoadedCards.add(new String[]{"ボールを落すと落ちる。", "If you drop the ball, it will fall.", "pre", "0.5", "1", "waiting", "ボールをおとすとおちる。"});
        preLoadedCards.add(new String[]{"電気を消すと暗くなる。", "If you turn off the lights, it will get dark.", "pre", "0.5", "1", "waiting", "でんきをけすとくらくなる。"});
        preLoadedCards.add(new String[]{"学校に行かないと友達と会えないよ。", "If you don't go to school, you can't meet your friends.", "pre", "0.5", "1", "waiting", "がっこうにいかないとともだちとあえないよ。"});
        preLoadedCards.add(new String[]{"たくさん食べると太るよ。", "If you eat a lot, you will get fat, for sure.", "pre", "0.5", "1", "waiting", "たくさんたべるとふとるよ。"});
        preLoadedCards.add(new String[]{"先生だと、きっと年上なんじゃないですか？", "If he's a teacher, he must be older for sure, right?", "pre", "0.5", "1", "waiting", "せんせいだと、きっととしうえなんじゃないですか？"});
        preLoadedCards.add(new String[]{"みんなが行くなら私も行く。", "If given that everybody is going, then I'll go too.", "pre", "0.5", "1", "waiting", "みんながいくならわたしもいく。"});
        preLoadedCards.add(new String[]{"アリスさんが言うなら問題ないよ。", "If given that Alice-san says so, there's no problem.", "pre", "0.5", "1", "waiting", "アリスさんがいうならもんだいないよ。"});
        preLoadedCards.add(new String[]{"図書館はどこですか。／図書館なら、あそこです。", "Where is the library? / If given that you're talking about the library, then it's over there.", "pre", "0.5", "1", "waiting", "としょかんはどこですか。／としょかんなら、あそこです。"});
        preLoadedCards.add(new String[]{"友達に会えれば、買い物に行きます。", "If I can meet with my friend, we will go shopping.", "pre", "0.5", "1", "waiting", "ともだちにあえれば、かいものにいきます。"});
        preLoadedCards.add(new String[]{"お金があればいいね。", "If I had money, it would be good, huh?", "pre", "0.5", "1", "waiting", "おかねがあればいいね。"});
        preLoadedCards.add(new String[]{"楽しければ、私も行く。", "If it's fun, I'll go too.", "pre", "0.5", "1", "waiting", "たのしければ、わたしもいく。"});
        preLoadedCards.add(new String[]{"楽しくなければ、私も行かない。", "If it's not fun, I'll also not go.", "pre", "0.5", "1", "waiting", "たのしくなければ、わたしもいかない。"});
        preLoadedCards.add(new String[]{"食べなければ病気になるよ。", "If you don't eat, you will become sick.", "pre", "0.5", "1", "waiting", "たべなければびょうきになるよ。"});
        preLoadedCards.add(new String[]{"暇だったら、遊びに行くよ。", "If I am free, I will go play.", "pre", "0.5", "1", "waiting", "ひまだったら、あそびにいくよ。"});
        preLoadedCards.add(new String[]{"学生だったら、学生割引で買えます。", "If you're a student, you can buy with a student discount.", "pre", "0.5", "1", "waiting", "がくせいだったら、がくせいわりびきでかえます。"});
        preLoadedCards.add(new String[]{"友達に会えれば、買い物に行きます。", "We will go shopping, if I can meet with my friend.", "pre", "0.5", "1", "waiting", "ともだちにあえれば、かいものにいきます。"});
        preLoadedCards.add(new String[]{"友達に会えたら、買い物に行きます。", "If I can meet with my friend, we will go shopping.", "pre", "0.5", "1", "waiting", "ともだちにあえたら、かいものにいきます。"});
        preLoadedCards.add(new String[]{"お金があればいいね。", "It would be good, if I had money, huh?", "pre", "0.5", "1", "waiting", "おかねがあればいいね。"});
        preLoadedCards.add(new String[]{"お金があったらいいね。", "If I had money, it would be good, huh?", "pre", "0.5", "1", "waiting", "おかねがったらいいね。"});
        preLoadedCards.add(new String[]{"家に帰ったら、誰もいなかった。", "When I went home, there was no one there. (unexpected result)", "pre", "0.5", "1", "waiting", "いえにかえったら、だれもいなかった。"});
        preLoadedCards.add(new String[]{"アメリカに行ったら、たくさん太りました。", "As a result of going to America, I got really fat. (unexpected result)", "pre", "0.5", "1", "waiting", "アメリカにいったら、たくさんふとりました。"});
        preLoadedCards.add(new String[]{"もしよかったら、映画を観に行きますか？", "If by any chance it's ok with you, go to watch movie?", "pre", "0.5", "1", "waiting", "もしよかったら、えいがをみにいきますか？"});
        preLoadedCards.add(new String[]{"もし時間がないなら、明日でもいいよ。", "If given that there's no time, tomorrow is fine as well. (Not certain whether there is no time)", "pre", "0.5", "1", "waiting", "もしじかんがないなら、あしたでもいいよ。"});
        preLoadedCards.add(new String[]{"ここに入ってはいけません。", "You must not enter here.", "pre", "0.5", "1", "waiting", "ここにはいってはいけません。"});
        preLoadedCards.add(new String[]{"それを食べてはだめ！", "You can't (must not) eat that!", "pre", "0.5", "1", "waiting", "それをたべてはだめ！"});
        preLoadedCards.add(new String[]{"夜、遅くまで電話してはならない。", "You must not use the phone until late at night.", "pre", "0.5", "1", "waiting", "よる、おそくまででんわしてはならない。"});
        preLoadedCards.add(new String[]{"早く寝てはなりませんでした。", "Wasn't allowed to sleep early.", "pre", "0.5", "1", "waiting", "はやくねてはなりませんでした。"});
        preLoadedCards.add(new String[]{"毎日学校に行かなくてはなりません。", "Must go to school everyday.", "pre", "0.5", "1", "waiting", "まいにちがっこうにいかなくてはなりません。"});
        preLoadedCards.add(new String[]{"宿題をしなくてはいけなかった。", "Had to do homework.", "pre", "0.5", "1", "waiting", "しゅくだいをしなくてはいけなかった。"});
        preLoadedCards.add(new String[]{"毎日学校に行かないとだめです。", "Must go to school everyday.", "pre", "0.5", "1", "waiting", "まいにちがっこうにいかないとだめです。"});
        preLoadedCards.add(new String[]{"宿題をしないといけない。", "Have to do homework.", "pre", "0.5", "1", "waiting", "しゅくだいをしないといけない。"});
        preLoadedCards.add(new String[]{"毎日学校に行かなければいけません。", "Must go to school everyday.", "pre", "0.5", "1", "waiting", "まいにちがっこうにいかなければいけません。"});
        preLoadedCards.add(new String[]{"宿題をしなければだめだった。", "Had to do homework.", "pre", "0.5", "1", "waiting", "しゅくだいをしなければだめだった。"});
        preLoadedCards.add(new String[]{"勉強しなくちゃ。", "Gotta study.", "pre", "0.5", "1", "waiting", "べんきょうしなくちゃ。"});
        preLoadedCards.add(new String[]{"ご飯を食べなきゃ。", "Gotta eat.", "pre", "0.5", "1", "waiting", "ごはんをたべなきゃ。"});
        preLoadedCards.add(new String[]{"学校に行かないと。", "Gotta go to school.", "pre", "0.5", "1", "waiting", "がっこうにいかないと。"});
        preLoadedCards.add(new String[]{"ここに入っちゃだめだよ。", "You can't enter here.", "pre", "0.5", "1", "waiting", "ここにはいっちゃだめだよ。"});
        preLoadedCards.add(new String[]{"死んじゃだめだよ！", "You can't die!", "pre", "0.5", "1", "waiting", "しんじゃだめだよ。"});
        preLoadedCards.add(new String[]{"全部食べてもいいよ。", "You can go ahead and eat it all. (lit: Even if you eat it all, it's good, you know.)", "pre", "0.5", "1", "waiting", "ぜんぶたべてもいいよ。"});
        preLoadedCards.add(new String[]{"全部食べなくてもいいよ。", "You don't have to eat it all. (lit: Even if you don't eat it all, it's good, you know.)", "pre", "0.5", "1", "waiting", "ぜんぶたべなくてもいいよ。"});
        preLoadedCards.add(new String[]{"全部飲んでも大丈夫だよ。", "It's OK if you drink it all. (lit: Even if you drink it all, it's OK, you know.)", "pre", "0.5", "1", "waiting", "ぜんぶのんでもだいじょうぶだよ。"});
        preLoadedCards.add(new String[]{"全部飲んでも構わないよ。", "I don't mind if you drink it all. (lit: Even if you drink it all, I don't mind, you know.)", "pre", "0.5", "1", "waiting", "ぜんぶのんでもかまわないよ。"});
        preLoadedCards.add(new String[]{"もう帰っていい？", "Can I go home already?", "pre", "0.5", "1", "waiting", "もうかえっていい？"});
        preLoadedCards.add(new String[]{"これ、ちょっと見ていい？", "Can I take a quick look at this?", "pre", "0.5", "1", "waiting", "これ、ちょっとみていい？"});
        preLoadedCards.add(new String[]{"何をしたいですか。", "What do you want to do?", "pre", "0.5", "1", "waiting", "なにをしたいですか。"});
        preLoadedCards.add(new String[]{"温泉に行きたい。", "I want to go to hot spring.", "pre", "0.5", "1", "waiting", "おんせんにいきたい。"});
        preLoadedCards.add(new String[]{"ケーキ、食べたくないの？", "You don't want to eat cake?", "pre", "0.5", "1", "waiting", "ケーキ、たべたくないの？"});
        preLoadedCards.add(new String[]{"食べたくなかったけど食べたくなった。", "I didn't want to eat it but I became wanting to eat.", "pre", "0.5", "1", "waiting", "たべたくなかったけどたべｔくなった。"});
        preLoadedCards.add(new String[]{"ずっと一緒にいたい。", "I want to be together forever. (lit: Want to exist together for long time.)", "pre", "0.5", "1", "waiting", "ずっといっしょにいたい。"});
        preLoadedCards.add(new String[]{"犬と遊びたいですか。", "Do you want to play with dog?", "pre", "0.5", "1", "waiting", "いぬとあそびたいですか。"});
        preLoadedCards.add(new String[]{"大きい縫いぐるみが欲しい！", "I want a big stuffed doll!", "pre", "0.5", "1", "waiting", "おおきいぬいぐるみがほしい！"});
        preLoadedCards.add(new String[]{"全部食べてほしいんだけど・・・。", "I want it all eaten but ...", "pre", "0.5", "1", "waiting", "ぜんぶたべてほしいんだけど・・・。"});
        preLoadedCards.add(new String[]{"部屋をきれいにしてほしいのよ。", "It is that I want the room cleaned up, you know.", "pre", "0.5", "1", "waiting", "へやをきれいにしてほしいのよ。"});
        preLoadedCards.add(new String[]{"今日は何をしようか？／テーマパークに行こう！", "What shall [we] do today? / Let's go to theme park!", "pre", "0.5", "1", "waiting", "あしたはなにをしようか？／テーマパークにいこう！"});
        preLoadedCards.add(new String[]{"明日は何を食べようか？／カレーを食べよう！", "What shall [we] eat tomorrow? / Let's eat curry!", "pre", "0.5", "1", "waiting", "あしたはなにをたべようか？／カレーをたべよう！"});
        preLoadedCards.add(new String[]{"今日は何をしましょうか？／テーマパークに行きましょう！", "What shall [we] do today? / Let's go to theme park!", "pre", "0.5", "1", "waiting", "あしたはなにをしましょうか？／テーマパークにいきましょう！"});
        preLoadedCards.add(new String[]{"明日は何を食べましょうか？／カレーを食べましょう！", "What shall [we] eat tomorrow? / Let's eat curry!", "pre", "0.5", "1", "waiting", "あしたはなにをたべましょうか？／カレーをたべましょう！"});
        preLoadedCards.add(new String[]{"銀行に行ったらどうですか。", "How about going to bank?", "pre", "0.5", "1", "waiting", "ぎんこうにいったらどうですか。"});
        preLoadedCards.add(new String[]{"たまにご両親と話せばどう？", "How about talking with your parents once in a while?", "pre", "0.5", "1", "waiting", "たまにごりょうしんとはなせばどう？"});
        preLoadedCards.add(new String[]{"アリスが、「寒い」と言った。", "Alice said, Cold.", "pre", "0.5", "1", "waiting", "アリスが、「さむい」といった。"});
        preLoadedCards.add(new String[]{"「今日は授業がない」と先生から聞いたんだけど。", "It is that I heard from the teacher, There is no class today.", "pre", "0.5", "1", "waiting", "「きょうはじゅぎょうがない」とせんせいからきいたんだけど。"});
        preLoadedCards.add(new String[]{"「寒い」とアリスが田中に言った。", "Cold, Alice said to Tanaka.", "pre", "0.5", "1", "waiting", "「さむい」とアリスがたなかにいった。"});
        preLoadedCards.add(new String[]{"先生から今日は授業がないと聞いたんだけど。", "I heard from the teacher that there is no class today.", "pre", "0.5", "1", "waiting", "せんせいからきょうはじゅぎょうがないときいたんだけど。"});
        preLoadedCards.add(new String[]{"これは、日本語で何と言いますか。", "What do you call this in Japanese? (lit: About this, what do you say in Japanese?)", "pre", "0.5", "1", "waiting", "これは、にほんごでなんといいますか。"});
        preLoadedCards.add(new String[]{"私は、アリスと言います。", "I am called Alice. (lit: As for me, you say Alice.)", "pre", "0.5", "1", "waiting", "わたしは、アリスといいます。"});
        preLoadedCards.add(new String[]{"カレーを食べようと思ったけど、食べる時間がなかった。", "I thought about setting out to eat curry but I didn't have time to eat.", "pre", "0.5", "1", "waiting", "カレーをたべようとおもったけど、たべるじかんがなかった。"});
        preLoadedCards.add(new String[]{"今、どこに行こうかと考えている。", "Now, I'm considering where to set out to go.", "pre", "0.5", "1", "waiting", "いま、どこにいこうかとかんがえている。"});
        preLoadedCards.add(new String[]{"彼は、これは何だと言いましたか。", "What did he say this is?", "pre", "0.5", "1", "waiting", "かれは、これはなんだといいましたか。"});
        preLoadedCards.add(new String[]{"彼は高校生だと聞いたけど、信じられない。", "I heard that he is a high school student but I can't believe it.", "pre", "0.5", "1", "waiting", "かれはこうこうせいだときいたけど、しんじられない。"});
        preLoadedCards.add(new String[]{"これは何だと言いましたか。", "What did [he] say this is?", "pre", "0.5", "1", "waiting", "これはなんだといいましたか。"});
        preLoadedCards.add(new String[]{"何と言いましたか。", "What did [he] say?", "pre", "0.5", "1", "waiting", "なんといいましたか。"});
        preLoadedCards.add(new String[]{"智子は来年、海外に行くんだって。", "Tomoko said that she's going overseas next year.", "pre", "0.5", "1", "waiting", "ともこはらいねん、かいがいにいくんだって。"});
        preLoadedCards.add(new String[]{"もうお金がないって。", "I already told you I have no money.", "pre", "0.5", "1", "waiting", "もうおかねがないって。"});
        preLoadedCards.add(new String[]{"え？何だって？", "Huh? What did you say?", "pre", "0.5", "1", "waiting", "え？なんだって？"});
        preLoadedCards.add(new String[]{"今、時間がないって聞いたんだけど、本当？", "I heard you don't have time now, is that true?", "pre", "0.5", "1", "waiting", "いま、じかんがないってきいたんだけど、ほんとう？"});
        preLoadedCards.add(new String[]{"今、時間がないって、本当？", "You don't have time now (I heard), is that true?", "pre", "0.5", "1", "waiting", "いま、じかんがないって、ほんとう？"});
        preLoadedCards.add(new String[]{"明日って、雨が降るんだって。", "About tomorrow, I hear that it's going to rain.", "pre", "0.5", "1", "waiting", "あしたって、あめがふるんだって。"});
        preLoadedCards.add(new String[]{"アリスって、すごくいい人でしょ？", "About Alice, she's a very good person, right?", "pre", "0.5", "1", "waiting", "アリスって、すごくいいひとでしょ？"});
        preLoadedCards.add(new String[]{"これは、なんという魚ですか。", "What is this fish referred to as?", "pre", "0.5", "1", "waiting", "これは、なんというさかなですか。"});
        preLoadedCards.add(new String[]{"この魚は、鯛といいます。", "This fish is known as Tai.", "pre", "0.5", "1", "waiting", "このさかなは、たいといいます。"});
        preLoadedCards.add(new String[]{"ルミネというデパートはどこにあるか、知っていますか？", "Do you know where the department store called Lumine is?", "pre", "0.5", "1", "waiting", "ルミネというデパートはどこにあるか、しっていますか？"});
        preLoadedCards.add(new String[]{"「友達」は、英語で「friend」という意味です。", "The meaning of tomodachi in English is friend.", "pre", "0.5", "1", "waiting", "「ともだち」は、えいがで「friend」といういみです。"});
        preLoadedCards.add(new String[]{"主人公が犯人だったというのが一番面白かった。", "The most interesting thing was that the main character was the criminal.", "pre", "0.5", "1", "waiting", "しゅじんこうがはんにんだったというのがいちばんおもしろかった。"});
        preLoadedCards.add(new String[]{"日本人はお酒に弱いというのは本当？", "Is it true that Japanese people are weak to alcohol?", "pre", "0.5", "1", "waiting", "にほんじんはおさけによわいというのはほんとう？"});
        preLoadedCards.add(new String[]{"独身だというのは、嘘だったの？", "It was a lie that you were single?", "pre", "0.5", "1", "waiting", "どくしんだというのは、うそだったの？"});
        preLoadedCards.add(new String[]{"リブートというのは、パソコンを再起動するということです。", "Reboot means to restart your computer.", "pre", "0.5", "1", "waiting", "リブートというのは、パソコンをさいきどうするということです。"});
        preLoadedCards.add(new String[]{"あんたは、いつもこういう時に来るんだから、困るんだよ。", "It's because you always come at times like these that I'm troubled.", "pre", "0.5", "1", "waiting", "あんたは、いつもこういうときにくるんだから、こまるんだよ。"});
        preLoadedCards.add(new String[]{"そういう人と一緒に仕事をするのは、嫌だよね。", "[Anybody would] dislike doing work together with that type of person, huh?", "pre", "0.5", "1", "waiting", "そういうひとといっしょにしごとをするのは、いやだよね。"});
        preLoadedCards.add(new String[]{"ああいう人と結婚できたら、幸せになれると思います。", "I think you can become happy if you could marry that type of person.", "pre", "0.5", "1", "waiting", "ああいうひととけっこんできたら、しあわせになれるとおもいます。"});
        preLoadedCards.add(new String[]{"大学に行かないって、どういう意味なの？", "What do you mean, You're not going to go to college?", "pre", "0.5", "1", "waiting", "だいがくにいかないって、どういういみなの？"});
        preLoadedCards.add(new String[]{"みきちゃんは、あんたの彼女でしょう？／う～ん、彼女というか、友達というか、なんというか・・・", "Miki-chan is your girlfriend, right? / Um, you might say girlfriend, or friend, or something ...", "pre", "0.5", "1", "waiting", "みきちゃんは、あんたのかのじょでしょう？／う～ん、かのじょというか、ともだちというか、なんというか・・・"});
        preLoadedCards.add(new String[]{"お酒は好きというか、ないと生きていけない。", "I like alcohol or rather, can't live on without it.", "pre", "0.5", "1", "waiting", "おさけはすきというか、ないといきていけない。"});
        preLoadedCards.add(new String[]{"多分行かないと思う。というか、お金がないから、行けない。", "Don't think I'll go. Or rather, can't because there's no money.", "pre", "0.5", "1", "waiting", "たぶんいかないとおもう。というか、おかねがないから、いけない。"});
        preLoadedCards.add(new String[]{"というか、もう帰らないとだめですけど。", "Rather than that, I have to go home already.", "pre", "0.5", "1", "waiting", "というか、もうかえらないとだめですけど。"});
        preLoadedCards.add(new String[]{"みきちゃんが洋介と別れたんだって。／ということは、みきちゃんは、今彼氏がいないということ？／ そう。そういうこと。", "I heard that Miki-chan broke up with Yousuke. / Does that mean Miki-chan doesn't have a boyfriend now? / That's right. That's what it means.", "pre", "0.5", "1", "waiting", "みきちゃんがようすけとわかれたんだって。／ということは、みきちゃんは、いまかれしがいないということ？／ そう。そういうこと。"});
        preLoadedCards.add(new String[]{"来年留学するというのは、智子のこと？", "The studying abroad next year thing, is that Tomoko?", "pre", "0.5", "1", "waiting", "らいねんりゅうがくするというのは、ともこのこと？"});
        preLoadedCards.add(new String[]{"来年留学するって智子のこと？", "The studying abroad next year thing, is that Tomoko?", "pre", "0.5", "1", "waiting", "らいねんりゅうがくするってともこのこと？"});
        preLoadedCards.add(new String[]{"しないとだめだよ。／だって、時間がないからできないよ。", "Have to do it, you know. / But (even so), can't do it because there is no time.", "pre", "0.5", "1", "waiting", "しないとだめだよ。／だって、じかんがないからできないよ。"});
        preLoadedCards.add(new String[]{"行かなくてもいいよ。／だって、みんな行くって。私も行かないと。", "Don't have to go, you know. / But (even so), everybody said they're going. I have to go too.", "pre", "0.5", "1", "waiting", "いかなくてもいいよ。／だって、みんないくって。わたしもいかないと。"});
        preLoadedCards.add(new String[]{"てことは、みきちゃんは、今彼氏がいないてこと？", "Does that mean Miki-chan doesn't have a boyfriend now?", "pre", "0.5", "1", "waiting", "てことは、みきちゃんは、いまかれしがいないてこと？"});
        preLoadedCards.add(new String[]{"ていうか、もう帰らないとだめですけど。", "Rather than that, I have to go home already.", "pre", "0.5", "1", "waiting", "ていうか、もうかえらないとだめですけど。"});
        preLoadedCards.add(new String[]{"みきちゃんが、明日こないって。", "Miki-chan says she isn't coming tomorrow.", "pre", "0.5", "1", "waiting", "みきちゃんが、あしたこないって。"});
        preLoadedCards.add(new String[]{"てゆうか、もう帰らないとだめですけど。", "Rather than that, I have to go home already.", "pre", "0.5", "1", "waiting", "てゆうか、もうかえらないとだめですけど。"});
        preLoadedCards.add(new String[]{"そうゆうことじゃないって！", "I said it's not like that (lit: it's not that type of thing)!", "pre", "0.5", "1", "waiting", "そうゆうことじゃないって！"});
        preLoadedCards.add(new String[]{"お好み焼きを始めて食べてみたけど、とてもおいしかった！", "I tried eating okonomiyaki for the first time and it was very tasty!", "pre", "0.5", "1", "waiting", "おこのみやきをはじめてたべてみたけど、とてもおいしかった！"});
        preLoadedCards.add(new String[]{"お酒を飲んでみましたが、すごく眠くなりました。", "I tried drinking alcohol and I became extremely sleepy.", "pre", "0.5", "1", "waiting", "おさけをのんでみましたが、すごくねむくなりました。"});
        preLoadedCards.add(new String[]{"新しいデパートに行ってみる。", "I'm going to check out the new department store.", "pre", "0.5", "1", "waiting", "あたらしいデパートにいってみる。"});
        preLoadedCards.add(new String[]{"広島のお好み焼きを食べてみたい！", "I want to try eating Hiroshima okonomiyaki!", "pre", "0.5", "1", "waiting", "ひろしまのおこのみやきをたべてみたい！"});
        preLoadedCards.add(new String[]{"毎日、勉強を避けようとする。", "Everyday, she attempts to avoid study.", "pre", "0.5", "1", "waiting", "まいにち、べんきょうをさけようとする。"});
        preLoadedCards.add(new String[]{"無理矢理に部屋に入ろうとしている。", "He is attempting to force his way into the room.", "pre", "0.5", "1", "waiting", "むりやりにへやにはいろうとしている。"});
        preLoadedCards.add(new String[]{"早く寝ようとしたけど、結局は徹夜した。", "I attempted to sleep early but ended up staying up all night.", "pre", "0.5", "1", "waiting", "はやくねようとしたけど、けっきょくはてつやした。"});
        preLoadedCards.add(new String[]{"お酒を飲もうとしたが、奥さんが止めた。", "He tried to drink alcohol but his wife stopped him.", "pre", "0.5", "1", "waiting", "おさけをのもうとしたが、おくさんがとめた。"});
        preLoadedCards.add(new String[]{"勉強をなるべく避けようと思った。", "I thought I would attempt to avoid studying as much as possible.", "pre", "0.5", "1", "waiting", "べんきょうをなるべくさけようとおもった。"});
        preLoadedCards.add(new String[]{"毎日ジムに行こうと決めた。", "Decided to attempt to go to gym everyday.", "pre", "0.5", "1", "waiting", "まいにちジムにいこうときめた。"});
        preLoadedCards.add(new String[]{"私が友達にプレゼントをあげた。", "I gave present to friend.", "pre", "0.5", "1", "waiting", "わたしがともだちにプレゼントをあげた。"});
        preLoadedCards.add(new String[]{"これは先生にあげる。", "I'll give this to teacher.", "pre", "0.5", "1", "waiting", "これはせんせいにあげる。"});
        preLoadedCards.add(new String[]{"車を買ってあげるよ。", "I'll give you the favor of buying a car.", "pre", "0.5", "1", "waiting", "くるまをかってあげるよ。"});
        preLoadedCards.add(new String[]{"代わりに行ってあげる。", "I'll give you the favor of going in your place.", "pre", "0.5", "1", "waiting", "かわりにいってあげる。"});
        preLoadedCards.add(new String[]{"学生がこれを先生にあげる。", "The student give this to teacher. (looking at it from the student's point of view)", "pre", "0.5", "1", "waiting", "がくせいがこれをせんせいにあげる。"});
        preLoadedCards.add(new String[]{"友達が父にいいことを教えてあげた。", "Friend gave the favor of teaching something good to my dad. (looking at it from the friend's point of view)", "pre", "0.5", "1", "waiting", "ともだちがちちにいいことをおしえてあげた。"});
        preLoadedCards.add(new String[]{"犬に餌をやった？", "Did you give the dog food?", "pre", "0.5", "1", "waiting", "いぬにえさをやった？"});
        preLoadedCards.add(new String[]{"友達が私にプレゼントをくれた。", "Friend gave present to me.", "pre", "0.5", "1", "waiting", "ともだちがわたしにプレゼントをくれた。"});
        preLoadedCards.add(new String[]{"これは、先生がくれた。", "Teacher gave this to me.", "pre", "0.5", "1", "waiting", "これは、せんせいがくれた。"});
        preLoadedCards.add(new String[]{"車を買ってくれるの？", "You'll give me the favor of buying a car for me?", "pre", "0.5", "1", "waiting", "くるまをかってくれるの？"});
        preLoadedCards.add(new String[]{"代わりに行ってくれる？", "Will you give me the favor of going in my place?", "pre", "0.5", "1", "waiting", "かわりにいってくれる？"});
        preLoadedCards.add(new String[]{"先生がこれを学生にくれる。", "The teacher give this to student. (looking at it from the student's point of view)", "pre", "0.5", "1", "waiting", "せんせいがこれをがくせいにくれる。"});
        preLoadedCards.add(new String[]{"友達が父にいいことを教えてくれた。", "Friend gave favor of teaching something good to my dad. (looking at it from the dad's point of view)", "pre", "0.5", "1", "waiting", "ともだちがちちにいいことをおしえてくれた。"});
        preLoadedCards.add(new String[]{"先生が教えてあげるんですか。", "Teacher, will you be the one to give favor of teaching to... [anybody other than the speaker]?", "pre", "0.5", "1", "waiting", "せんせいがおしえてあげるんですか。"});
        preLoadedCards.add(new String[]{"先生が教えてくれるんですか。", "Teacher, will you be the one to give favor of teaching to... [anybody including the speaker]?", "pre", "0.5", "1", "waiting", "せんせいがおしえてくれるんですか。"});
        preLoadedCards.add(new String[]{"私が全部食べてあげました。", "I gave favor of eating it all.", "pre", "0.5", "1", "waiting", "わたしがぜんぶたべてあげました。"});
        preLoadedCards.add(new String[]{"友達がプレゼントを私にくれた。", "Friend gave present to me.", "pre", "0.5", "1", "waiting", "ともだちがプレゼントをわたしにくれた。"});
        preLoadedCards.add(new String[]{"私が友達にプレゼントをもらった。", "I received present from friend.", "pre", "0.5", "1", "waiting", "わたしがともだちにプレゼントをもらった。"});
        preLoadedCards.add(new String[]{"友達からプレゼントをもらった。", "I received present from friend.", "pre", "0.5", "1", "waiting", "ともだちからプレゼントをもらった。"});
        preLoadedCards.add(new String[]{"これは友達に買ってもらった。", "About this, received the favor of buying it from friend.", "pre", "0.5", "1", "waiting", "これはともだちにかってもらった。"});
        preLoadedCards.add(new String[]{"宿題をチェックしてもらいたかったけど、時間がなくて無理だった。", "I wanted to receive the favor of checking homework but there was no time and it was impossible.", "pre", "0.5", "1", "waiting", "しゅくだいをチェックしてもらいたかったけど、じかんがなくてむりだった。"});
        preLoadedCards.add(new String[]{"その時計は私からもらったのよ。", "[He] received that watch from me.", "pre", "0.5", "1", "waiting", "そのとけいはわたしからもらったのよ。"});
        preLoadedCards.add(new String[]{"千円を貸してくれる？", "Will you give me the favor of lending 1000 yen?", "pre", "0.5", "1", "waiting", "せんえんをかしてくれる。"});
        preLoadedCards.add(new String[]{"千円を貸してもらえる？", "Can I receive the favor of you lending 1000 yen?", "pre", "0.5", "1", "waiting", "せんえんをかしてもらえる？"});
        preLoadedCards.add(new String[]{"ちょっと静かにしてくれない？", "Won't you be a little quieter?", "pre", "0.5", "1", "waiting", "ちょっとしずかにしてくれない？"});
        preLoadedCards.add(new String[]{"漢字を書いてもらえませんか。", "Can you write this in kanji for me?", "pre", "0.5", "1", "waiting", "かんじをかいてもらえませんか。"});
        preLoadedCards.add(new String[]{"全部食べないでくれますか。", "Can you not eat it all?", "pre", "0.5", "1", "waiting", "ぜんぶたべないでくれますか。"});
        preLoadedCards.add(new String[]{"高い物を買わないでくれる？", "Can you not buy expensive thing(s)?", "pre", "0.5", "1", "waiting", "たかいものをかわないでくれる？"});
        preLoadedCards.add(new String[]{"それをください。", "Please give me that.", "pre", "0.5", "1", "waiting", "それをください。"});
        preLoadedCards.add(new String[]{"それをくれる？", "Can you give me that?", "pre", "0.5", "1", "waiting", "それをくれる？"});
        preLoadedCards.add(new String[]{"漢字で書いてください。", "Please write it in kanji.", "pre", "0.5", "1", "waiting", "かんじでかいてください。"});
        preLoadedCards.add(new String[]{"ゆっくり話してください。", "Please speak slowly.", "pre", "0.5", "1", "waiting", "ゆっくりはなしてください。"});
        preLoadedCards.add(new String[]{"落書きを書かないでください。", "Please don't write graffiti.", "pre", "0.5", "1", "waiting", "らくがきをかかないでください。"});
        preLoadedCards.add(new String[]{"ここにこないでください。", "Please don't come here.", "pre", "0.5", "1", "waiting", "ここにこないでください。"});
        preLoadedCards.add(new String[]{"日本語で話して。", "Please speak in Japanese.", "pre", "0.5", "1", "waiting", "にほんごではなして。"});
        preLoadedCards.add(new String[]{"消しゴムを貸して。", "Please lend me the eraser.", "pre", "0.5", "1", "waiting", "けしゴムをかして。"});
        preLoadedCards.add(new String[]{"遠い所に行かないで。", "Please don't go to a far place.", "pre", "0.5", "1", "waiting", "とおいところにいかないで。"});
        preLoadedCards.add(new String[]{"日本語で話してくれ。", "Speak in Japanese.", "pre", "0.5", "1", "waiting", "にほんごではなしてくれ。"});
        preLoadedCards.add(new String[]{"消しゴムを貸してくれ。", "Lend me the eraser.", "pre", "0.5", "1", "waiting", "けしゴムをかしてくれ。"});
        preLoadedCards.add(new String[]{"遠い所に行かないでくれ。", "Don't go to a far place.", "pre", "0.5", "1", "waiting", "とおいところにいかないでくれ。"});
        preLoadedCards.add(new String[]{"お父さんがくれた時計が壊れた。", "The clock that father gave broke.", "pre", "0.5", "1", "waiting", "おとうさんがくれたとけいがこわれた。"});
        preLoadedCards.add(new String[]{"「それをください」とお父さんが言った。", "Father said, Please give me that.", "pre", "0.5", "1", "waiting", "「それをください」とおとうさんがいった。"});
        preLoadedCards.add(new String[]{"スプーンをちょうだい。", "Please give me the spoon.", "pre", "0.5", "1", "waiting", "スプーンをちょうだい。"});
        preLoadedCards.add(new String[]{"ここに名前を書いてちょうだい。", "Please write your name here.", "pre", "0.5", "1", "waiting", "ここになまえをかいてちょうだい。"});
        preLoadedCards.add(new String[]{"よく聞きなさい！", "Listen well!", "pre", "0.5", "1", "waiting", "よくききなさい！"});
        preLoadedCards.add(new String[]{"ここに座りなさい。", "Sit here.", "pre", "0.5", "1", "waiting", "ここにすわりなさい。"});
        preLoadedCards.add(new String[]{"まだいっぱいあるから、たくさん食べな。", "There's still a lot, so eat a lot.", "pre", "0.5", "1", "waiting", "まだいっぱいあるから、たくさんたべな。"});
        preLoadedCards.add(new String[]{"それでいいと思うなら、そうしなよ。", "If you think that's fine, then go ahead and do it.", "pre", "0.5", "1", "waiting", "それでいいとおもうなら、そうしなよ。"});
        preLoadedCards.add(new String[]{"好きにしろ。", "Do as you please.", "pre", "0.5", "1", "waiting", "すきにしろ。"});
        preLoadedCards.add(new String[]{"あっち行け！", "Go away!", "pre", "0.5", "1", "waiting", "あっちいけ！"});
        preLoadedCards.add(new String[]{"早く酒を持ってきてくれ。", "Hurry up and bring me some alcohol.", "pre", "0.5", "1", "waiting", "はやくさけをもってきてくれ。"});
        preLoadedCards.add(new String[]{"それを食べるな！", "Don't eat that!", "pre", "0.5", "1", "waiting", "それをたべるな！"});
        preLoadedCards.add(new String[]{"変なことを言うな！", "Don't say such weird things!", "pre", "0.5", "1", "waiting", "へんなことをいうな！"});
        preLoadedCards.add(new String[]{"今、図書館に行くんだよな。／うん、なんで？", "You are going to the library now huh? (seeking explanation) / Yeah, why?", "pre", "0.5", "1", "waiting", "いま、としょかんにいくんだよな。／うん、 なんで？"});
        preLoadedCards.add(new String[]{"日本語は、たくさん勉強したけどな。まだ全然わからない。／大丈夫よ。きっとわかるようになるからさ。／ならいいけどな。", "I studied Japanese a lot, right? But, I still don't get it at all. / No problem. You'll become able to understand for sure, you know? / If so, it would be good.", "pre", "0.5", "1", "waiting", "にほんごは、たくさんべんきょうしたけどな。まだぜんぜんわからない。／だいじょうぶよ。きっとわかるようになるからさ。／ならいいけどな。"});
        preLoadedCards.add(new String[]{"今日は雨が降るかな？", "I wonder if it'll rain today.", "pre", "0.5", "1", "waiting", "きょうはあめがふるかな？"});
        preLoadedCards.add(new String[]{"いい大学に行けるかな？", "I wonder if I can go to a good college.", "pre", "0.5", "1", "waiting", "いいだいがくにいけるかな？"});
        preLoadedCards.add(new String[]{"もう時間が ないわ。", "There is no more time.", "pre", "0.5", "1", "waiting", "もうじかんがないわ。"});
        preLoadedCards.add(new String[]{"おい、行くぞ！", "Hey, we're going!", "pre", "0.5", "1", "waiting", "おい、いくぞ！"});
        preLoadedCards.add(new String[]{"これで、もう終わりだぜ。", "With this, it's over already.", "pre", "0.5", "1", "waiting", "これで、もうおわりだぜ。"});
        preLoadedCards.add(new String[]{"いい大学に入れるかしら？", "I wonder if I can enter a good college.", "pre", "0.5", "1", "waiting", "いいだいがくにはいれるかしら？"});
        preLoadedCards.add(new String[]{"全部食べさせた。", "Made/Let (someone) eat it all.", "pre", "0.5", "1", "waiting", "ぜんぶたべさせた。"});
        preLoadedCards.add(new String[]{"全部食べさせてくれた。", "Let (someone) eat it all.", "pre", "0.5", "1", "waiting", "せんぶたべさせてくれた。"});
        preLoadedCards.add(new String[]{"先生が学生に宿題をたくさんさせた。", "Teacher made students do lots of homework.", "pre", "0.5", "1", "waiting", "先生ががくせいにしゅくだいをたくさんさせた。"});
        preLoadedCards.add(new String[]{"先生が質問をたくさん聞かせてくれた。", "Teacher let [someone] ask lots of questions.", "pre", "0.5", "1", "waiting", "せんせいがしつもんをたくさんきかせてくれた。"});
        preLoadedCards.add(new String[]{"今日は仕事を休ませてください。", "Please let me rest from work today. (Please let me take the day off today.)", "pre", "0.5", "1", "waiting", "きょうはしごとをやすませてください。"});
        preLoadedCards.add(new String[]{"その部長は、よく長時間働かせる。", "That manager often make [people] work long hours.", "pre", "0.5", "1", "waiting", "そのぶちょうは、よくちょうじかんはたらかせる。"});
        preLoadedCards.add(new String[]{"トイレに行かせてくれますか。", "Can you let me go to the bathroom? (Sounds like a prisoner, even in English)", "pre", "0.5", "1", "waiting", "トイレにいかせてくれますか。"});
        preLoadedCards.add(new String[]{"トイレに行ってもいいですか。", "Is it ok to go to the bathroom? (No problem here)", "pre", "0.5", "1", "waiting", "といれにいってもいいですか。"});
        preLoadedCards.add(new String[]{"同じことを何回も言わすな！", "Don't make me say the same thing again and again!", "pre", "0.5", "1", "waiting", "おなじことをなんかいもいわすな！"});
        preLoadedCards.add(new String[]{"お腹空いているんだから、なんか食べさしてくれよ。", "I'm hungry so let me eat something.", "pre", "0.5", "1", "waiting", "おなかあいているんだから、なんかたべさしてくれよ。"});
        preLoadedCards.add(new String[]{"ポリッジが誰かに食べられた！", "The porridge was eaten by somebody!", "pre", "0.5", "1", "waiting", "ポリッジがだれかにたべられた。"});
        preLoadedCards.add(new String[]{"みんなに変だと言われます。", "I am told by everybody that [I'm] strange.", "pre", "0.5", "1", "waiting", "みんなにへんだといわれます。"});
        preLoadedCards.add(new String[]{"光の速さを超えるのは、不可能だと思われる。", "Exceeding the speed of light is thought to be impossible.", "pre", "0.5", "1", "waiting", "ひかりのはやさをこえるのは、ふかのうだとおもわれる。"});
        preLoadedCards.add(new String[]{"この教科書は多くの人に読まれている。", "This textbook is being read by a large number of people.", "pre", "0.5", "1", "waiting", "このきょうかしょはおおくのひとによまれている。"});
        preLoadedCards.add(new String[]{"外国人に質問を聞かれたが、答えられなかった。", "I was asked a question by a foreigner but I couldn't answer.", "pre", "0.5", "1", "waiting", "がいこくじんにしつもんをきかれたが、こたえられなかった。"});
        preLoadedCards.add(new String[]{"このパッケージには、あらゆるものが含まれている。", "Everything is included in this package.", "pre", "0.5", "1", "waiting", "このパッケージには、あらゆるものがふくまれている。"});
        preLoadedCards.add(new String[]{"レシートはどうされますか？", "What about your receipt? (lit: How will you do receipt?)", "pre", "0.5", "1", "waiting", "レシートはどうされますか？"});
        preLoadedCards.add(new String[]{"明日の会議に行かれるんですか？", "Are you going to tomorrow's meeting?", "pre", "0.5", "1", "waiting", "あしたのかいぎにいかれるんですか？"});
        preLoadedCards.add(new String[]{"朝ご飯は食べたくなかったのに、食べさせられた。", "Despite not wanting to eat breakfast, I was made to eat it.", "pre", "0.5", "1", "waiting", "あさごはんはたべたくなかったのに、たべさせられた。"});
        preLoadedCards.add(new String[]{"日本では、お酒を飲ませられることが多い。", "In Japan, the event of being made to drink is numerous.", "pre", "0.5", "1", "waiting", "にほんでは、おさけをのませられることがおおい。"});
        preLoadedCards.add(new String[]{"あいつに二時間も待たせられた。", "I was made to wait 2 hours by that guy.", "pre", "0.5", "1", "waiting", "あいつににじかんもまたせられた。"});
        preLoadedCards.add(new String[]{"親に毎日宿題をさせられる。", "I am made to do homework everyday by my parent(s).", "pre", "0.5", "1", "waiting", "おやにまいにちしょくだいをさせられる。"});
        preLoadedCards.add(new String[]{"学生が廊下に立たされた。", "The student was made to stand in the hall.", "pre", "0.5", "1", "waiting", "がくせいがろうかにたたされた。"});
        preLoadedCards.add(new String[]{"日本では、お酒を飲まされることが多い。", "In Japan, the event of being made to drink is numerous.", "pre", "0.5", "1", "waiting", "にほんでは、おさけをのまされることがおおい。"});
        preLoadedCards.add(new String[]{"あいつに二時間も待たされた。", "I was made to wait 2 hours by that guy.", "pre", "0.5", "1", "waiting", "あいつににじかんもまたされた。"});
        preLoadedCards.add(new String[]{"アリスさん、もう召し上がりましたか。", "Alice-san, did [you] eat already?", "pre", "0.5", "1", "waiting", "アリスさん、もうめしあがりましたか。"});
        preLoadedCards.add(new String[]{"仕事で何をなさっているんですか。", "What are you doing at work?", "pre", "0.5", "1", "waiting", "しごとでなにをなさっているんですか。"});
        preLoadedCards.add(new String[]{"推薦状を書いてくださるんですか。", "You're going to give me the favor of writing a recommendation letter?", "pre", "0.5", "1", "waiting", "すいせんじょうをかいてくださるんですか。"});
        preLoadedCards.add(new String[]{"どちらからいらっしゃいましたか。", "Where did you come from?", "pre", "0.5", "1", "waiting", "どちらからいらっしゃいましたか。"});
        preLoadedCards.add(new String[]{"今日は、どちらへいらっしゃいますか。", "Where are you going today?", "pre", "0.5", "1", "waiting", "きょうは、どちらへいらっしゃいますか。"});
        preLoadedCards.add(new String[]{"私はキムと申します。", "As for me, [people] say Kim. (I am called Kim.)", "pre", "0.5", "1", "waiting", "わたしはキムともうします。"});
        preLoadedCards.add(new String[]{"私が書いたレポートを見ていただけますか。", "Will I be able to receive the favor of getting my report looked at?", "pre", "0.5", "1", "waiting", "わたしがかいたレポートをみていただけますか。"});
        preLoadedCards.add(new String[]{"失礼致します。", "Excuse me. (lit: I am doing a discourtesy.)", "pre", "0.5", "1", "waiting", "しつれいします。"});
        preLoadedCards.add(new String[]{"こちらは、私の部屋です。", "Over here is my room.", "pre", "0.5", "1", "waiting", "こちらは、わたしのへやです。"});
        preLoadedCards.add(new String[]{"こちらは、私の部屋でございます。", "This way is my room.", "pre", "0.5", "1", "waiting", "こちらは、わたしのへやでございます。"});
        preLoadedCards.add(new String[]{"お手洗いはこのビルの二階にあります。", "The bathroom is in the second floor of this building.", "pre", "0.5", "1", "waiting", "おてあらいはこのビルのにかいにあります。"});
        preLoadedCards.add(new String[]{"お手洗いはこのビルの二階にございます。", "The bathroom is in the second floor of this building.", "pre", "0.5", "1", "waiting", "おてあらいはこのビルのにかいにございます。"});
        preLoadedCards.add(new String[]{"先生はお見えになりますか。", "Have you seen the teacher?", "pre", "0.5", "1", "waiting", "せんせいほかえになりますか。"});
        preLoadedCards.add(new String[]{"もうお帰りですか。", "You're going home already?", "pre", "0.5", "1", "waiting", "もうおかえりですか。"});
        preLoadedCards.add(new String[]{"店内でお召し上がりですか。", "Will you be dining in?", "pre", "0.5", "1", "waiting", "てんないでおめしあがりですか。"});
        preLoadedCards.add(new String[]{"少々お待ちください。", "Please wait a moment.", "pre", "0.5", "1", "waiting", "しょうしょうおたちください。"});
        preLoadedCards.add(new String[]{"こちらにご覧下さい。", "Please look this way.", "pre", "0.5", "1", "waiting", "こちらにごらんください。"});
        preLoadedCards.add(new String[]{"閉まるドアにご注意下さい。", "Please be careful of the closing doors.", "pre", "0.5", "1", "waiting", "しまるドアにごちゅういください。"});
        preLoadedCards.add(new String[]{"よろしくお願いします。", "I properly make request.", "pre", "0.5", "1", "waiting", "よろしくおねがいします。"});
        preLoadedCards.add(new String[]{"先生、お聞きしたいことがありますが。", "Teacher, there's something I want to ask you.", "pre", "0.5", "1", "waiting", "せんせい、おききしたいことがありますが。"});
        preLoadedCards.add(new String[]{"すみません、お待たせしました。", "Sorry, I made you wait (causative form).", "pre", "0.5", "1", "waiting", "すみません、おまたせしました。"});
        preLoadedCards.add(new String[]{"千円からお預かりいたします。", "We'll be holding on [from?] your 1000 yen.", "pre", "0.5", "1", "waiting", "せんえんからおあずかりいたします。"});
        preLoadedCards.add(new String[]{"いらっしゃいませ。", "Please come in!", "pre", "0.5", "1", "waiting", "いらっしゃいませ。"});
        preLoadedCards.add(new String[]{"いらっしゃい！", "Please come in!", "pre", "0.5", "1", "waiting", "いらっしゃい！"});
        preLoadedCards.add(new String[]{"ありがとうございました。またお越しくださいませ。", "Thank you very much. Please come again.", "pre", "0.5", "1", "waiting", "ありがとうございました。またおこしくださいませ。"});
        preLoadedCards.add(new String[]{"どうぞ、ごゆっくりなさいませ。", "Please take your time and relax.", "pre", "0.5", "1", "waiting", "どうぞ、ごゆっくりなさいませ。"});
        preLoadedCards.add(new String[]{"宿題をやった？／しまった！", "Did you do homework? / Oh no! (I screwed up!)", "pre", "0.5", "1", "waiting", "しゅくだいをやった？／しまった！"});
        preLoadedCards.add(new String[]{"そのケーキを全部食べてしまった。", "Oops, I ate that whole cake.", "pre", "0.5", "1", "waiting", "そのケーキをぜんぶたべてしまった。"});
        preLoadedCards.add(new String[]{"毎日ケーキを食べて、２キロ太ってしまいました。", "I ate cake everyday and I (unintentionally) gained two kilograms.", "pre", "0.5", "1", "waiting", "まいにちケーキをたべて、２キロふとってしまいました。"});
        preLoadedCards.add(new String[]{"ちゃんと食べないと、痩せてしまいますよ。", "If you don't eat properly, you'll (unintentionally) lost weight you know.", "pre", "0.5", "1", "waiting", "ちゃんとたべないと、やせてしまいますよ。"});
        preLoadedCards.add(new String[]{"結局、嫌なことをさせてしまった。", "In the end, I (unintentionally) made [someone] do something distasteful.", "pre", "0.5", "1", "waiting", "けっきょく、いやなことをさせてしまった。"});
        preLoadedCards.add(new String[]{"ごめん、待たせてしまって！", "Sorry about (unintentionally) making you wait!", "pre", "0.5", "1", "waiting", "ごめん、またせてしまって！"});
        preLoadedCards.add(new String[]{"金魚がもう死んでしまった。", "The goldfish died already (oops).", "pre", "0.5", "1", "waiting", "きんぎょもうしんでしまった。"});
        preLoadedCards.add(new String[]{"金魚がもう死んじゃった。", "The goldfish died already.", "pre", "0.5", "1", "waiting", "きんぎょがもうしんじゃった。"});
        preLoadedCards.add(new String[]{"もう帰っちゃっていい？", "Is it OK if I went home already?", "pre", "0.5", "1", "waiting", "もうかえちゃっていい？"});
        preLoadedCards.add(new String[]{"みんな、どっか行っちゃったよ。", "Everybody went off somewhere.", "pre", "0.5", "1", "waiting", "みんな、どっかいっちゃったよ。"});
        preLoadedCards.add(new String[]{"そろそろ遅くなっちゃうよ。", "It'll gradually become late, you know.", "pre", "0.5", "1", "waiting", "そろそろおそくなっちゃうよ。"});
        preLoadedCards.add(new String[]{"また遅刻しちまったよ。", "Darn, I'm late again.", "pre", "0.5", "1", "waiting", "またちこくしちまったよ。"});
        preLoadedCards.add(new String[]{"ごめん、ついお前を呼んじまった。", "Sorry, I just ended up calling you unconsciously.", "pre", "0.5", "1", "waiting", "ごめん、ついおまえをよんじまった。"});
        preLoadedCards.add(new String[]{"宿題をやってしまいなさい。", "Finish your homework completely.", "pre", "0.5", "1", "waiting", "しゅくだいをやってしまいなさい。"});
        preLoadedCards.add(new String[]{"徹夜して、宿題することはある。", "There are times when I do homework while staying up all night.", "pre", "0.5", "1", "waiting", "てつやして、しゅくだいすることはある。"});
        preLoadedCards.add(new String[]{"一人で行くことはありません。", "I never go by myself.", "pre", "0.5", "1", "waiting", "ひとりでいくことはありません。"});
        preLoadedCards.add(new String[]{"パリに行ったことはありますか。", "Have you ever gone to Paris?", "pre", "0.5", "1", "waiting", "バリにいったことはありますか。"});
        preLoadedCards.add(new String[]{"お寿司を食べたことがある。", "I've had sushi before.", "pre", "0.5", "1", "waiting", "おすしをたべたことがある。"});
        preLoadedCards.add(new String[]{"日本の映画を観たことないの？", "You've never seen a Japanese movie?", "pre", "0.5", "1", "waiting", "にほんのえいがをみたことないの？"});
        preLoadedCards.add(new String[]{"ヨーロッパに行ったことがあったらいいな。", "It would be nice if I ever go to Europe.", "pre", "0.5", "1", "waiting", "ヨーロッパにいったことがあったらいいな。"});
        preLoadedCards.add(new String[]{"そういうのを見たことがなかった。", "I had never seen anything like that.", "pre", "0.5", "1", "waiting", "そういうのをみたことがなかった。"});
        preLoadedCards.add(new String[]{"一度行ったこともないんです。", "I've never gone, not even once.", "pre", "0.5", "1", "waiting", "いちどいったこともないんです。"});
        preLoadedCards.add(new String[]{"早くきて。映画は、今ちょうどいいところだよ。", "Come quickly. We're at the good part of the movie.", "pre", "0.5", "1", "waiting", "はやくきて。えいがは、いまちょうどいいところだよ。"});
        preLoadedCards.add(new String[]{"彼は、優しいところもあるよ。", "His personality has some gentle parts too.", "pre", "0.5", "1", "waiting", "かれは、やさしいところもあるよ。"});
        preLoadedCards.add(new String[]{"今は授業が終ったところです。", "Class has ended just now.", "pre", "0.5", "1", "waiting", "いまはじゅぎょうがおわったところです。"});
        preLoadedCards.add(new String[]{"これから行くところでした。", "I was just about to go from now.", "pre", "0.5", "1", "waiting", "これからいくところでした。"});
        preLoadedCards.add(new String[]{"どうしてこなかったの？／授業があったの。", "Why didn't (you) come? / I had class. (feminine explanatory)", "pre", "0.5", "1", "waiting", "どうしてこなっかったの？／じゅぎょうがあったの。"});
        preLoadedCards.add(new String[]{"どうしてこなかったの？／授業があったもの。", "Why didn't (you) come? / I had class. (feminine explanatory)", "pre", "0.5", "1", "waiting", "どうしてこなっかったの？／じゅぎょうがあったもの。"});
        preLoadedCards.add(new String[]{"どうしてこなかったの？／授業があったもん。", "Why didn't (you) come? / I had class, so there. (feminine explanatory)", "pre", "0.5", "1", "waiting", "どうしてこなっかったの？／じゅぎょうがあったもん。"});
        preLoadedCards.add(new String[]{"スミスさんは食堂に行ったかもしれません。", "Smith-san may have gone to the cafeteria.", "pre", "0.5", "1", "waiting", "スミスさんはしょくどうにいったかもしれません。"});
        preLoadedCards.add(new String[]{"雨で試合は中止になるかもしれないね。", "The game may become canceled by rain, huh?", "pre", "0.5", "1", "waiting", "あめでしあいはちゅうしになるかもしれないね。"});
        preLoadedCards.add(new String[]{"この映画は一回見たことあるかも！", "I might have already seen this movie once.", "pre", "0.5", "1", "waiting", "このえいがはいっかいみたことあるかも！"});
        preLoadedCards.add(new String[]{"あそこが代々木公園かもしれない。", "That might be Yoyogi park over there.", "pre", "0.5", "1", "waiting", "あそこがよよぎこうえんかもしれない。"});
        preLoadedCards.add(new String[]{"もう逃げられないかもしれんぞ。", "Might not be able to escape anymore, you know.", "pre", "0.5", "1", "waiting", "もうにげられないかもしれんぞ。"});
        preLoadedCards.add(new String[]{"明日も雨でしょう。", "Probably rain tomorrow too.", "pre", "0.5", "1", "waiting", "あしたもあめでしょう。"});
        preLoadedCards.add(new String[]{"あなたは、学生さんでしょうか。", "Are (you) student?", "pre", "0.5", "1", "waiting", "あなたは、がくせいさんでしょうか。"});
        preLoadedCards.add(new String[]{"これからどこへ行くんでしょうか？", "Where (are you) going from here?", "pre", "0.5", "1", "waiting", "これからどこへいくんでしょうか？"});
        preLoadedCards.add(new String[]{"休ませていただけますでしょうか。", "May I receive the favor of resting, possibly?", "pre", "0.5", "1", "waiting", "やすませていただけますでしょうか。"});
        preLoadedCards.add(new String[]{"あっ！遅刻しちゃう！／だから、時間がないって言ったでしょう！", "Ah! We're going to be late! / That's why I told you there was no time!", "pre", "0.5", "1", "waiting", "あっ！ちこくしちゃう！／だから、じかんがないっていったでしょう！"});
        preLoadedCards.add(new String[]{"これから食べに行くんでしょ。／だったら？", "You're going to eat from now aren't you? / So what if I am?", "pre", "0.5", "1", "waiting", "これからたべにいくんでしょ。／だったら？"});
        preLoadedCards.add(new String[]{"掃除、手伝ってくれるでしょう。／え？そうなの？", "You're going to help me clean, right? / Huh? Is that so?", "pre", "0.5", "1", "waiting", "そうじ、てつだってくれるでしょう。／え？そうなの？"});
        preLoadedCards.add(new String[]{"アリスはどこだ？／もう寝ているだろう。", "Where is Alice? / Probably sleeping already.", "pre", "0.5", "1", "waiting", "アリスはどこだ？もうねているだろう。"});
        preLoadedCards.add(new String[]{"もう家に帰るんだろう。／そうよ。", "You're going home already, right? / That's right.", "pre", "0.5", "1", "waiting", "もう{うち|いえ}にかえるんだろう。／そうよ。"});
        preLoadedCards.add(new String[]{"りんごだけ。", "Just apple(s) (and nothing else).", "pre", "0.5", "1", "waiting", "りんごだけ。"});
        preLoadedCards.add(new String[]{"これとそれだけ。", "Just that and this (and nothing else).", "pre", "0.5", "1", "waiting", "これとそれだけ。"});
        preLoadedCards.add(new String[]{"それだけは、食べないでください。", "Just don't eat that. (Anything else is assumed to be OK).", "pre", "0.5", "1", "waiting", "それだけは、たべないでください。"});
        preLoadedCards.add(new String[]{"この歌だけを歌わなかった。", "Didn't sing just this song.", "pre", "0.5", "1", "waiting", "このうただけをうたわなかった。"});
        preLoadedCards.add(new String[]{"その人だけが好きだったんだ。", "That person was the only person I liked.", "pre", "0.5", "1", "waiting", "そのひとだけがすきだったんだ。"});
        preLoadedCards.add(new String[]{"この販売機だけでは、500円玉が使えない。", "Cannot use 500 yen coin in just this vending machine.", "pre", "0.5", "1", "waiting", "このはんばいきだけは、500えんだまがつかえない。"});
        preLoadedCards.add(new String[]{"小林さんからだけには、返事が来なかった。", "A reply has not come from only Kobayashi-san (topic + target).", "pre", "0.5", "1", "waiting", "こばやしさんからだけには、へんじがこなかった。"});
        preLoadedCards.add(new String[]{"準備が終わったから、これからは食べるだけだ。", "Since the preparations are done, from here we just have to eat.", "pre", "0.5", "1", "waiting", "じゅんびがおわったから、これからはたべるだけだ。"});
        preLoadedCards.add(new String[]{"ここに名前を書くだけでいいですか？", "Is it ok to just write [my] name here?", "pre", "0.5", "1", "waiting", "ここになまえをかくだけでいいですか？"});
        preLoadedCards.add(new String[]{"この乗車券は発売当日のみ有効です。", "This boarding ticket is only valid on the date on which it was purchased.", "pre", "0.5", "1", "waiting", "このじょうしゃけんははつばいとうじつのみゆうこうです。"});
        preLoadedCards.add(new String[]{"アンケート対象は大学生のみです。", "The targets of this survey are only college students.", "pre", "0.5", "1", "waiting", "アンケートたいしょうはだいがくのみです。"});
        preLoadedCards.add(new String[]{"これしかない。", "There's nothing but this.", "pre", "0.5", "1", "waiting", "これしかない。"});
        preLoadedCards.add(new String[]{"これだけ見る。", "See just this.", "pre", "0.5", "1", "waiting", "これだけみる。"});
        preLoadedCards.add(new String[]{"これだけ見ない。", "Don't see just this.", "pre", "0.5", "1", "waiting", "これだけみない。"});
        preLoadedCards.add(new String[]{"これしか見ない。", "Don't see anything else but this.", "pre", "0.5", "1", "waiting", "これしかみない。"});
        preLoadedCards.add(new String[]{"今日は忙しくて、朝ご飯しか食べられなかった。", "Today was busy and couldn't eat anything but breakfast.", "pre", "0.5", "1", "waiting", "きょうはいそがしくて、あさごはんしかたべられなかった。"});
        preLoadedCards.add(new String[]{"全部買うの？／ううん、これだけ。", "You're buying everything? / Nah, just this.", "pre", "0.5", "1", "waiting", "ぜんぶかうの？／ううん、これだけ。"});
        preLoadedCards.add(new String[]{"全部買うの？／ううん、これしか買わない。", "You're buying everything? / Nah, won't buy anything else but this.", "pre", "0.5", "1", "waiting", "ぜんぶかうの？／ううん、これしかかわない。"});
        preLoadedCards.add(new String[]{"アリスからしか何ももらってない。", "I didn't receive anything except from Alice.", "pre", "0.5", "1", "waiting", "アリスからしかなにももらってない。"});
        preLoadedCards.add(new String[]{"これから頑張るしかない！", "There's nothing to do but try our best!", "pre", "0.5", "1", "waiting", "これからがんばるしかない！"});
        preLoadedCards.add(new String[]{"こうなったら、逃げるしかない。", "There no choice but to run away once it turns out like this.", "pre", "0.5", "1", "waiting", "こうなったら、にげるしかない。"});
        preLoadedCards.add(new String[]{"もう腐っているから、捨てるしかないよ。", "It's rotten already so there's nothing to do but throw it out.", "pre", "0.5", "1", "waiting", "もうくさっているから、すてるしかないよ。"});
        preLoadedCards.add(new String[]{"これは買うっきゃない！", "There's nothing but to buy this!", "pre", "0.5", "1", "waiting", "これはかうっきゃない！"});
        preLoadedCards.add(new String[]{"こうなったら、もうやるっきゃない！", "If things turn out like this, there nothing to do but to just do it!", "pre", "0.5", "1", "waiting", "こうなったら、もうやるっきゃない！"});
        preLoadedCards.add(new String[]{"何だよ！おばさんばっかりじゃないか？", "What the? Isn't it nothing but obasan?", "pre", "0.5", "1", "waiting", "なんだよ！おばさんばっかりじゃないか？"});
        preLoadedCards.add(new String[]{"いやだ。おばさんばっかり。", "Eww. It's nothing but obasan.", "pre", "0.5", "1", "waiting", "いやだ。おばさんばっかり。"});
        preLoadedCards.add(new String[]{"崇君は漫画ばっかり読んでてさ。かっこ悪い。", "Takashi-kun is reading nothing but comic books... He's so uncool.", "pre", "0.5", "1", "waiting", "たかしくんはまんがばっかりよんでてさ。かっこわるい。"});
        preLoadedCards.add(new String[]{"彼は麻雀ばかりです。", "He's nothing but mahjong. (He does nothing but play mahjong.)", "pre", "0.5", "1", "waiting", "かれはマージャンばかりです。"});
        preLoadedCards.add(new String[]{"直美ちゃんと遊ぶばっかりでしょう！", "You're hanging out with Naomi-chan all the time, aren't you!", "pre", "0.5", "1", "waiting", "なおみちゃんとあそぶばっかりでしょう！"});
        preLoadedCards.add(new String[]{"最近は仕事ばっかだよ。", "Lately, it's nothing but work.", "pre", "0.5", "1", "waiting", "さいきんはしごとばっかだよ。"});
        preLoadedCards.add(new String[]{"佐藤さんは料理が上手で、また食べ過ぎました。", "Satou-san is good at cooking and I ate too much again.", "pre", "0.5", "1", "waiting", "さとうさんはりょうりがじょうずで、またたべすぎました。"});
        preLoadedCards.add(new String[]{"お酒を飲みすぎないように気をつけてね。", "Be careful to not drink too much, ok?", "pre", "0.5", "1", "waiting", "おさけをのみすぎないようにきをつけてね。"});
        preLoadedCards.add(new String[]{"大きすぎるからトランクに入らないぞ。", "It won't fit in the trunk cause it's too big, man.", "pre", "0.5", "1", "waiting", "おおきすぎるからトランクに入らないぞ。"});
        preLoadedCards.add(new String[]{"静かすぎる。罠かもしれないよ。", "It's too quiet. It might be a trap, you know.", "pre", "0.5", "1", "waiting", "しずかすぎる。わなかもしれないよ。"});
        preLoadedCards.add(new String[]{"時間が足りなさすぎて、何もできなかった。", "Due to too much of a lack of time, I couldn't do anything.", "pre", "0.5", "1", "waiting", "じかんがたりなさすぎて、なにもできなかった。"});
        preLoadedCards.add(new String[]{"彼には、彼女がもったいなさすぎるよ。", "She is totally wasted on him (too good for him).", "pre", "0.5", "1", "waiting", "かれには、かのじょがもったいなさすぎるよ。"});
        preLoadedCards.add(new String[]{"昨晩のこと、全然覚えてないな。／それは飲みすぎだよ。", "Man, I don't remember anything about last night. / That's drinking too much.", "pre", "0.5", "1", "waiting", "さくばんのこと、ぜんぜんおぼえてないな。／せれはのみすぎだよ。"});
        preLoadedCards.add(new String[]{"昨日、電話三回もしたよ！", "I called you like three times yesterday!", "pre", "0.5", "1", "waiting", "きのう、でんわさんかいもしたよ！"});
        preLoadedCards.add(new String[]{"試験のために三時間も勉強した。", "I studied three whole hours for the exam.", "pre", "0.5", "1", "waiting", "しけんのためにさんじかんもべんきょうした。"});
        preLoadedCards.add(new String[]{"今年、十キロも太っちゃった！", "I gained 10 whole kilograms this year!", "pre", "0.5", "1", "waiting", "ことし、じゅっキロもふとちゃった！"});
        preLoadedCards.add(new String[]{"今日の天気はそれほど寒くない。", "Today's weather is not cold to that extent.", "pre", "0.5", "1", "waiting", "きょうのてんきはそれほどさむくない。"});
        preLoadedCards.add(new String[]{"寝る時間がないほど忙しい。", "Busy to the extent that there's no time to sleep.", "pre", "0.5", "1", "waiting", "ねるじかんがないほどいそがしい。"});
        preLoadedCards.add(new String[]{"韓国料理は食べれば食べるほど、おいしくなる。", "About Korean food, the more you eat the tastier it becomes.", "pre", "0.5", "1", "waiting", "かんこくりょうりはたべればたべるほど、おいしくなる。"});
        preLoadedCards.add(new String[]{"歩いたら歩くほど、迷ってしまった。", "The more I walked, the more I got lost.", "pre", "0.5", "1", "waiting", "あるいたらあるくほど、まよってしまった。"});
        preLoadedCards.add(new String[]{"勉強をすればするほど、頭がよくなるよ。", "The more you study, the more you will become smarter.", "pre", "0.5", "1", "waiting", "べんきょうをすればするほど、あたまがよくなるよ。"});
        preLoadedCards.add(new String[]{"iPodは、ハードディスクの容量が大きければ大きいほどもっとたくさんの曲が保存できます。", "About iPod, the larger the hard disk capacity, the more songs you can save.", "pre", "0.5", "1", "waiting", "iPodは、ハードディスクのようりょうがおおきければおおきいほどもっとたくさんのきょくがほぞんできます。"});
        preLoadedCards.add(new String[]{"航空券は安ければ安いほどいいとは限らない。", "It's not necessarily the case that the cheaper the ticket, the better it is.", "pre", "0.5", "1", "waiting", "こうくうけんはやすければやすいほどいいとはかぎらない。"});
        preLoadedCards.add(new String[]{"文章は、短ければ短いほど、簡単なら簡単なほどよいです。", "The shorter and simpler the sentences, the better it is.", "pre", "0.5", "1", "waiting", "ぶんしょうは、みじかければみじかいほど、かんたんならかんたんなほどよいです。"});
        preLoadedCards.add(new String[]{"このビルの高さは何ですか？", "What is the height of this building?", "pre", "0.5", "1", "waiting", "このビルのたかさはなんですか？"});
        preLoadedCards.add(new String[]{"犬の聴覚の敏感さを人間と比べると、はるかに上だ。", "If you compare the level of sensitivity of hearing of dogs to humans, it is far above.", "pre", "0.5", "1", "waiting", "いぬのちょうかくのびんかんさをにんげんとくらべると、はるかにうえだ。"});
        preLoadedCards.add(new String[]{"ここには、誰もいないようだ。", "Looks like no one is here.", "pre", "0.5", "1", "waiting", "ここには、だれもいないようだ。"});
        preLoadedCards.add(new String[]{"映画を観たようです。", "Looks like [he] watched the movie.", "pre", "0.5", "1", "waiting", "えいがをみたようです。"});
        preLoadedCards.add(new String[]{"学生のようだ。", "Looks like it's a student.", "pre", "0.5", "1", "waiting", "がくせいのようだ。"});
        preLoadedCards.add(new String[]{"ここは静かなようだ。", "Looks like it's quiet.", "pre", "0.5", "1", "waiting", "ここはしずかなようだ。"});
        preLoadedCards.add(new String[]{"あの人を見たような気がした。", "Had a feeling like I saw that person before.", "pre", "0.5", "1", "waiting", "あのひとをみたようなきがした。"});
        preLoadedCards.add(new String[]{"彼は学生のような雰囲気ですね。", "He has a student-like atmosphere.", "pre", "0.5", "1", "waiting", "かれはがくせいのようなふんいきですね。"});
        preLoadedCards.add(new String[]{"ちょっと怒ったように聞こえた。", "Was able to hear it like (she) was a little mad.", "pre", "0.5", "1", "waiting", "ちょっとおこったようにきこえた。"});
        preLoadedCards.add(new String[]{"何も起こらなかったように言った。", "Said (it) like nothing happened.", "pre", "0.5", "1", "waiting", "なにもおこらなかったようにいった。"});
        preLoadedCards.add(new String[]{"もう売り切れみたい。", "Looks like it's sold out already.", "pre", "0.5", "1", "waiting", "もううりきれみたい。"});
        preLoadedCards.add(new String[]{"制服を着ている姿をみると、学生みたいです。", "Looking at the uniform-wearing figure, (person) looks like a student.", "pre", "0.5", "1", "waiting", "せいふくをきているすがたをみると、がくせいみたいです。"});
        preLoadedCards.add(new String[]{"このピザはお好み焼きみたいじゃない？", "Doesn't this pizza looks like okonomiyaki?", "pre", "0.5", "1", "waiting", "このピザはおこのみやきみたいじゃない？"});
        preLoadedCards.add(new String[]{"喫茶店に行くみたいだった。", "It looked like (we) were going to a coffee shop.", "pre", "0.5", "1", "waiting", "きっさてんにいくみたいだった。"});
        preLoadedCards.add(new String[]{"秘密を教えてくれるみたいじゃなかった？", "It didn't look like (she) was going to tell the secret?", "pre", "0.5", "1", "waiting", "ひみつをおしえてくれるみたいじゃなかった？"});
        preLoadedCards.add(new String[]{"もう売り切れのようだ。", "It appears that it is sold-out already.", "pre", "0.5", "1", "waiting", "もううりきれのようだ。"});
        preLoadedCards.add(new String[]{"このピザはお好み焼きのように見える。", "This pizza looks like okonomiyaki.", "pre", "0.5", "1", "waiting", "このピザはおこのみやきのようにみえる。"});
        preLoadedCards.add(new String[]{"バランスが崩れて、一瞬倒れそうだった。", "Losing my balance, I seemed likely to fall for a moment.", "pre", "0.5", "1", "waiting", "バランスがくずれて、いっしゅんたおれそうだった。"});
        preLoadedCards.add(new String[]{"この辺りにありそうだけどな。", "It seems likely that it would be around here but...", "pre", "0.5", "1", "waiting", "このあたりにありそうだけどな。"});
        preLoadedCards.add(new String[]{"この漬物はおいしそう！", "I bet this pickled vegetable is tasty! (This pickled vegetable looks good!)", "pre", "0.5", "1", "waiting", "このつけものはおいしそう！"});
        preLoadedCards.add(new String[]{"これも結構よさそうだけど、やっぱり高いよね。", "This one also seems to be good but, as expected, it's expensive, huh?", "pre", "0.5", "1", "waiting", "これもけっこうよさそうだけど、やっぱりたかいよね。"});
        preLoadedCards.add(new String[]{"お前なら、金髪の女が好きそうだな。", "Knowing you, I bet you like blond-haired girls.", "pre", "0.5", "1", "waiting", "おまえなら、きんぱつのおんながすきそうだな。"});
        preLoadedCards.add(new String[]{"もう10時になったから、来なさそうだね。", "Since it already became 10:00, it's likely that (person) won't come.", "pre", "0.5", "1", "waiting", "もう10じになったから、こなさそうだね。"});
        preLoadedCards.add(new String[]{"これはただの試合じゃなさそうだ。", "This isn't likely to be an ordinary match.", "pre", "0.5", "1", "waiting", "これはただのしあいじゃなさそうだ。"});
        preLoadedCards.add(new String[]{"その人は学生でしょう。", "That person is probably student.", "pre", "0.5", "1", "waiting", "そのひとはがくせいでしょう。"});
        preLoadedCards.add(new String[]{"その人は学生だろう。", "That person is probably student.", "pre", "0.5", "1", "waiting", "そのひとはがくせいだろう。"});
        preLoadedCards.add(new String[]{"この犬はかわいそう。", "Oh, this poor dog.", "pre", "0.5", "1", "waiting", "このいぬはかわいそう。"});
        preLoadedCards.add(new String[]{"この犬はかわいい。", "This dog is cute.", "pre", "0.5", "1", "waiting", "このいぬ犬はかわいい。"});
        preLoadedCards.add(new String[]{"明日、雨が降るそうだ。", "I hear that it's going to rain tomorrow.", "pre", "0.5", "1", "waiting", "あした、あめがふるそうだ。"});
        preLoadedCards.add(new String[]{"毎日会いに行ったそうです。", "I heard he went to meet everyday.", "pre", "0.5", "1", "waiting", "まいにちあいにいったそうです。"});
        preLoadedCards.add(new String[]{"彼は、高校生だそうです。", "I hear that he is a high school student.", "pre", "0.5", "1", "waiting", "かれは、こうこうせいだそうです。"});
        preLoadedCards.add(new String[]{"今日、田中さんはこないの？／だそうです。", "Is Tanaka-san not coming today? / So I hear.", "pre", "0.5", "1", "waiting", "きょう、たなかさんはこないの？／だそうです。"});
        preLoadedCards.add(new String[]{"今日、田中さんはこないの？／こないらしい。", "Is Tanaka-san not coming today? / Seems like it (based on what I heard).", "pre", "0.5", "1", "waiting", "きょう、たなかさんはこないの？／こないらしい。"});
        preLoadedCards.add(new String[]{"あの人は何なの？／美由紀さんの友達らしいですよ。", "What is that person over there? / Seems to be Miyuki-san's friend (based on what I heard).", "pre", "0.5", "1", "waiting", "あのひとはなんなの？／みゆきさんのともだちらしいですよ。"});
        preLoadedCards.add(new String[]{"あの子は子供らしくない。", "That child does not act like a child.", "pre", "0.5", "1", "waiting", "あのこはこどもらしくない。"});
        preLoadedCards.add(new String[]{"大人らしくするつもりだったのに、大騒ぎしてしまった。", "Despite the fact that I planned to act like an adult, I ended up making a big ruckus.", "pre", "0.5", "1", "waiting", "おとならしくするつもりだったのに、おおさわぎしてしまった。"});
        preLoadedCards.add(new String[]{"あの人はちょっと韓国人っぽいよね。", "That person looks like a Korean person, huh?", "pre", "0.5", "1", "waiting", "あのひとはちょっとかんこくじんっぽいよね。"});
        preLoadedCards.add(new String[]{"みんなで、もう全部食べてしまったっぽいよ。", "It appears that everybody ate everything already.", "pre", "0.5", "1", "waiting", "みんなで、もうぜんぶたべてしまったっぽいよ。"});
        preLoadedCards.add(new String[]{"恭子は全然女っぽくないね。", "Kyouko is not womanly at all, huh?", "pre", "0.5", "1", "waiting", "きょうこはぜんぜんおんなっぽくないね。"});
        preLoadedCards.add(new String[]{"ご飯の方がおいしい。", "Rice is tastier. (lit: The way of rice is tasty.)", "pre", "0.5", "1", "waiting", "ごはんのほうがおいしい。"});
        preLoadedCards.add(new String[]{"鈴木さんの方が若い。", "Suzuki-san is younger. (lit: The way of Suzuki is young.)", "pre", "0.5", "1", "waiting", "すずきさんのほうがわかい。"});
        preLoadedCards.add(new String[]{"学生じゃない方がいいよ。", "It's better to not be a student. (lit: The way of not being student is good.)", "pre", "0.5", "1", "waiting", "がくせいじゃないほうがいいよ。"});
        preLoadedCards.add(new String[]{"赤ちゃんは、静かな方が好き。", "Like quiet babies more. (lit: About babies, the quiet way is desirable.)", "pre", "0.5", "1", "waiting", "あかちゃんは、しずかなほうがすき。"});
        preLoadedCards.add(new String[]{"ゆっくり食べた方が健康にいいよ。", "It's better for your health to eat slowly.", "pre", "0.5", "1", "waiting", "ゆっくりたべたほうがけんこうにいいよ。"});
        preLoadedCards.add(new String[]{"こちらから行った方が早かった。", "It was faster to go from this way.", "pre", "0.5", "1", "waiting", "こちらからいったほうがはやかった。"});
        preLoadedCards.add(new String[]{"マトリックス・レボリューションを観ない方がいいよ。", "It's better not to watch Matrix Revolution.", "pre", "0.5", "1", "waiting", "マトリックス・レボリューションをみないほうがいいよ。"});
        preLoadedCards.add(new String[]{"そんなに飲まなかった方がよかった。", "It was better not to have drunk that much.", "pre", "0.5", "1", "waiting", "そんなにのまなかったほうがよかった。"});
        preLoadedCards.add(new String[]{"花より団子。", "Dango rather than flowers. (This is a very famous proverb.)", "pre", "0.5", "1", "waiting", "はなよりだんご。"});
        preLoadedCards.add(new String[]{"ご飯の方が、パンよりおいしい。", "Rice tastes better than bread. (lit: The rice way is tasty as opposed to bread.)", "pre", "0.5", "1", "waiting", "ごはんのほうが、パンよりおいしい。"});
        preLoadedCards.add(new String[]{"キムさんより鈴木さんの方が若い。", "Suzuki-san is younger than Kim-san. (lit: The way of Suzuki is young as opposed to Kim-san.)", "pre", "0.5", "1", "waiting", "キムさんよりすずきさんのほうがわかい。"});
        preLoadedCards.add(new String[]{"毎日仕事に行くのが嫌だ。／仕事がないよりましだよ。", "I don't like going to work everyday. / It's not as bad as opposed to not having a job.", "pre", "0.5", "1", "waiting", "まいにちしごとにいくのがいやだ。／しごとがないよりましだよ。"});
        preLoadedCards.add(new String[]{"ゆっくり食べた方が早く食べるよりいい。", "It is better to eat slowly as opposed to eating quickly.", "pre", "0.5", "1", "waiting", "ゆっくりたべたほうがはやくたべるよりいい。"});
        preLoadedCards.add(new String[]{"商品の品質を何より大切にしています。", "We place value in product's quality over anything else.", "pre", "0.5", "1", "waiting", "しょうひんのひんしつをなによりたいせつにしています。"});
        preLoadedCards.add(new String[]{"この仕事は誰よりも早くできます。", "Can do this job more quickly than anyone else.", "pre", "0.5", "1", "waiting", "このしごとはだれよりもはやくできます。"});
        preLoadedCards.add(new String[]{"新宿の行き方は分かりますか。", "Do you know the way to go to Shinjuku?", "pre", "0.5", "1", "waiting", "しんじゅくのいきかたはわかりますか。"});
        preLoadedCards.add(new String[]{"そういう食べ方は体によくないよ。", "Eating in that way is not good for your body.", "pre", "0.5", "1", "waiting", "そういうたべかたはからだによくないよ。"});
        preLoadedCards.add(new String[]{"漢字の書き方を教えてくれますか？", "Can you teach me the way of writing kanji?", "pre", "0.5", "1", "waiting", "かんじのかきかたをおしえてくれますか？"});
        preLoadedCards.add(new String[]{"パソコンの使い方は、みんな知っているでしょう。", "Probably everybody knows the way to use PC's.", "pre", "0.5", "1", "waiting", "パソコンのつかいかたは、みんなしっているでしょう。"});
        preLoadedCards.add(new String[]{"人によって話が違う。", "The story is different depending on the person.", "pre", "0.5", "1", "waiting", "ひとによってはなしがちがう。"});
        preLoadedCards.add(new String[]{"季節によって果物はおいしくなったり、まずくなったりする。", "Fruit becomes tasty or nasty depending on the season.", "pre", "0.5", "1", "waiting", "きせつによってくだものはおいしくなったり、まずくなったりする。"});
        preLoadedCards.add(new String[]{"今日は飲みに行こうか？／それは、裕子によるね。", "Shall we go drinking today? / That depends on Yuuko.", "pre", "0.5", "1", "waiting", "きょうはのみにいこうか？／それは、ゆうこによるね。"});
        preLoadedCards.add(new String[]{"天気予報によると、今日は雨だそうだ。", "According to the weather forecast, I hear today is rain.", "pre", "0.5", "1", "waiting", "てんきよほうによると、きょうはあめだそうだ。"});
        preLoadedCards.add(new String[]{"友達の話によると、朋子はやっとボーイフレンドを見つけたらしい。", "According to a friend's story, it appears that Tomoko finally found a boyfriend.", "pre", "0.5", "1", "waiting", "ともだちのはなしによると、ともこはやっとボーイフレンドをみつけたらしい。"});
        preLoadedCards.add(new String[]{"この字は読みにくい 。", "This hand-writing is hard to read.", "pre", "0.5", "1", "waiting", "このじはよみにくい。"});
        preLoadedCards.add(new String[]{"カクテルはビールより飲みやすい。", "Cocktails are easier to drink than beer.", "pre", "0.5", "1", "waiting", "カクテルはビールよりのみやすい。"});
        preLoadedCards.add(new String[]{"部屋が暗かったので、見にくかった。", "Since the room was dark, it was hard to see.", "pre", "0.5", "1", "waiting", "へやがくらかったので、みにくかった。"});
        preLoadedCards.add(new String[]{"あの肉は食べにくい。", "That meat is hard to eat.", "pre", "0.5", "1", "waiting", "あのにくはたべにくい。"});
        preLoadedCards.add(new String[]{"あの肉を食べるのは難しい。", "The thing of eating that meat is difficult.", "pre", "0.5", "1", "waiting", "あのにくをたべるのはむずかしい。"});
        preLoadedCards.add(new String[]{"彼との忘れがたい思い出を大切にしている。", "I am treating importantly the hard to forget memories of and with him.", "pre", "0.5", "1", "waiting", "かれとのわすれがたいおもいでをたいせつにしている。"});
        preLoadedCards.add(new String[]{"とても信じがたい話だが、本当に起こったらしい。", "It's a very difficult to believe story but it seems (from hearsay) that it really happened.", "pre", "0.5", "1", "waiting", "とてもしんじがたいはなしだが、ほんとうにおこったらしい。"});
        preLoadedCards.add(new String[]{"日本語は読みづらいな。", "Man, Japanese is hard to read.", "pre", "0.5", "1", "waiting", "にほんごはよみづらいな。"});
        preLoadedCards.add(new String[]{"待ち合わせは、分かりづらい場所にしないでね。", "Please don't pick a difficult to understand location for the meeting arrangement.", "pre", "0.5", "1", "waiting", "まちあわせは、わかりづらいばしょにしないでね。"});
        preLoadedCards.add(new String[]{"何も食べないで寝ました。", "Went to sleep without eating anything.", "pre", "0.5", "1", "waiting", "なにもたべないでねました。"});
        preLoadedCards.add(new String[]{"歯を磨かないで、学校に行っちゃいました。", "Went to school without brushing teeth (by accident).", "pre", "0.5", "1", "waiting", "はをみがかないで、がっこうにいっちゃいました。"});
        preLoadedCards.add(new String[]{"宿題をしないで、授業に行くのは、やめた方がいいよ。", "It's better to stop going to class without doing homework.", "pre", "0.5", "1", "waiting", "しゅくだいをしないで、じゅぎょうにいくのは、やめたほうがいいよ。"});
        preLoadedCards.add(new String[]{"先生と相談しないで、この授業を取ることは出来ない。", "You cannot take this class without consulting with teacher.", "pre", "0.5", "1", "waiting", "せんせいとそうだんしないで、このじゅぎょうをとることはできない。"});
        preLoadedCards.add(new String[]{"彼は何も言わず、帰ってしまった。", "He went home without saying anything.", "pre", "0.5", "1", "waiting", "かれはなにもいわず、かえってしまった。"});
        preLoadedCards.add(new String[]{"何も食べずにそんなにお酒を飲むと当然酔っ払いますよ。", "Obviously, you're going to get drunk if you drink that much without eating anything.", "pre", "0.5", "1", "waiting", "なにもたべずにそんなにおさけをのむととうぜんよっぱらいますよ。"});
        preLoadedCards.add(new String[]{"勉強せずに東大に入れると思わないな。", "I don't think you can get in Tokyo University without studying.", "pre", "0.5", "1", "waiting", "べんきょうせずにとうだいにはいれるとおもわないな。"});
        preLoadedCards.add(new String[]{"すまん。", "Sorry.", "pre", "0.5", "1", "waiting", "すまん。"});
        preLoadedCards.add(new String[]{"韓国人と結婚しなくてはならん！", "You must marry a Korean!", "pre", "0.5", "1", "waiting", "かんこくじんとけっこんしなくてはならん！"});
        preLoadedCards.add(new String[]{"そんなことはさせん！", "I won't let you do such a thing!", "pre", "0.5", "1", "waiting", "そんなことはさせん！"});
        preLoadedCards.add(new String[]{"皆、今日行くって、知らんかったよ。", "I didn't know everybody was going today.", "pre", "0.5", "1", "waiting", "みんな、きょういくって、しらんかったよ。"});
        preLoadedCards.add(new String[]{"韓国人と結婚してはならぬ！", "You must not marry a Korean!", "pre", "0.5", "1", "waiting", "かんこくじんとけっこんしてはならぬ！"});
        preLoadedCards.add(new String[]{"模擬試験に何回も失敗して、実際に受けてみたら思わぬ結果が出た。", "After having failed mock examination any number of times, a result I wouldn't have thought came out when I actually tried taking the test.", "pre", "0.5", "1", "waiting", "もぎしけんになんかいもしっぱいして、じっさいにうけてみたらおもわぬけっかがでた。"});
        preLoadedCards.add(new String[]{"いくら英語を勉強しても、うまくならないの。／つまり、語学には、能力がないという訳か。／失礼ね。", "No matter how much I study, I don't become better at English. / So basically, it means that you don't have ability at language. / How rude.", "pre", "0.5", "1", "waiting", "いくらえいごをべんきょうしても、うまくならないの。／つまり、ごがくには、のうりょくがないというわけか。しつれいね。"});
        preLoadedCards.add(new String[]{"中国語が読めるわけがない。", "There's no way I can read Chinese. (lit: There is no reasoning for [me] to be able to read Chinese.)", "pre", "0.5", "1", "waiting", "ちゅうごくごがよめるわけがない。"});
        preLoadedCards.add(new String[]{"広子の家に行ったことある？／あるわけないでしょう。", "Have you ever gone to Hiroko's house? / There's no way I would have ever gone to her house, right?", "pre", "0.5", "1", "waiting", "広子のいえにいったことある？／あるわけないでしょう。"});
        preLoadedCards.add(new String[]{"微積分は分かる？／分かるわけないよ！", "Do you understand (differential and integral) calculus? / There's no way I would understand!", "pre", "0.5", "1", "waiting", "ひせきぶんはわかる？／わかるわけないよ。"});
        preLoadedCards.add(new String[]{"ここの試験に合格するのはわけない。", "It's easy to pass the tests here.", "pre", "0.5", "1", "waiting", "ここのしあいにごうかくするのはわけない。"});
        preLoadedCards.add(new String[]{"今度は負けるわけにはいかない。", "This time, I must not lose at all costs.", "pre", "0.5", "1", "waiting", "こんどはまけるわけにはいかない。"});
        preLoadedCards.add(new String[]{"ここまできて、あきらめるわけにはいかない。", "After coming this far, I must not give up.", "pre", "0.5", "1", "waiting", "ここまできて、あきらめるわけにはいかない。"});
        preLoadedCards.add(new String[]{"明日に行くとする。", "Assume we go tomorrow.", "pre", "0.5", "1", "waiting", "あしたにいくとする。"});
        preLoadedCards.add(new String[]{"今から行くとしたら、９時に着くと思います。", "If we suppose that we go from now, I think we will arrive at 9:00.", "pre", "0.5", "1", "waiting", "いまからいくとしたら、くじにつくとおもいます。"});
        preLoadedCards.add(new String[]{"観客として参加させてもらった。", "Received favor of allowing to participate as spectator.", "pre", "0.5", "1", "waiting", "かんきゃくとしてさんかさせてもらった。"});
        preLoadedCards.add(new String[]{"被害者としては、非常に幸いだった。", "As a victim, was extremely fortunate.", "pre", "0.5", "1", "waiting", "ひがいしゃとしては、ひじょうにさいわいだった。"});
        preLoadedCards.add(new String[]{"朝ご飯を食べたとしても、もう昼だからお腹が空いたでしょう。", "Even assuming that you ate breakfast, because it's already noon, you're probably hungry, right?", "pre", "0.5", "1", "waiting", "あさごはんをたべたとしても、もうひるだからおなかがすいたでしょう。"});
        preLoadedCards.add(new String[]{"すみません、今食べたばかりなので、お腹がいっぱいです。", "Sorry, but I'm full having just eaten.", "pre", "0.5", "1", "waiting", "すみません、いまたべたばかりなので、おなかがいっぱいです。"});
        preLoadedCards.add(new String[]{"10キロを走ったばかりで、凄く疲れた。", "I just ran 10 kilometers and am really tired.", "pre", "0.5", "1", "waiting", "じゅっキロをはしったばかりで、すごくつかれた。"});
        preLoadedCards.add(new String[]{"今、家に帰ったばかりです。", "I got back home just now.", "pre", "0.5", "1", "waiting", "いま、{いえ;うち}にかえったばかりです。"});
        preLoadedCards.add(new String[]{"昼ご飯を食べたばっかなのに、もうお腹が空いた。", "Despite the fact that I just ate lunch, I'm hungry already.", "pre", "0.5", "1", "waiting", "ひるごはんをたべたばっかなのに、もうおなかがすいた。"});
        preLoadedCards.add(new String[]{"まさか、今起きたばっかなの？", "No way, did you wake up just now?", "pre", "0.5", "1", "waiting", "まさか、いまおきたばっかなの？"});
        preLoadedCards.add(new String[]{"窓を開けたとたんに、猫が跳んでいった。", "As soon as I opened window, cat jumped out.", "pre", "0.5", "1", "waiting", "まどをあけたととんに、ねこがとんでいった。"});
        preLoadedCards.add(new String[]{"テレビを観ながら、宿題をする。", "Do homework while watching TV.", "pre", "0.5", "1", "waiting", "テレビをみながら、しゅくだいをする。"});
        preLoadedCards.add(new String[]{"音楽を聴きながら、学校へ歩くのが好き。", "Like to walk to school while listening to music.", "pre", "0.5", "1", "waiting", "おんがくをききながら、がっこうへあるくのがすき。"});
        preLoadedCards.add(new String[]{"相手に何も言わないながら、自分の気持ちをわかってほしいのは単なるわがままだと思わない？", "Don't you think that wanting the other person to understand one's feelings while not saying anything is just simply selfishness?", "pre", "0.5", "1", "waiting", "あいてになにもいわないながら、じぶんのきもちをわかってほしいのはたんなるわがままだとおもわない？"});
        preLoadedCards.add(new String[]{"ポップコーンを食べながら、映画を観る。", "Watch movie while eating popcorn.", "pre", "0.5", "1", "waiting", "ポップコーンをたべながら、えいがをみる。"});
        preLoadedCards.add(new String[]{"ポップコーンを食べながら、映画を観た。", "Watched movie while eating popcorn.", "pre", "0.5", "1", "waiting", "ポップコーンをたべながら、えいがをみた。"});
        preLoadedCards.add(new String[]{"口笛をしながら、手紙を書いていた。", "Was writing letter while whistling.", "pre", "0.5", "1", "waiting", "くちぶえをしながら、てがみをかいていた。"});
        preLoadedCards.add(new String[]{"仕事がいっぱい入って、残念ながら、今日は行けなくなりました。", "While it's unfortunate, a lot of work came in and it became so that I can't go today.", "pre", "0.5", "1", "waiting", "しごとがいっぱいはいって、ざんねんながら、きょうはいけなくなりました。"});
        preLoadedCards.add(new String[]{"貧乏ながらも、高級なバッグを買っちゃったよ。", "Even while I'm poor, I ended up buying a high quality bag.", "pre", "0.5", "1", "waiting", "びんぼうながらも、こうきゅうなバッグをかっちゃったよ。"});
        preLoadedCards.add(new String[]{"彼は、初心者ながらも、実力はプロと同じだ。", "Even while he is a beginner, his actual skills are the same as a pro.", "pre", "0.5", "1", "waiting", "かれは、しょしんしゃながらも、じつりょくはプロとおなじだ。"});
        preLoadedCards.add(new String[]{"ゲームにはまっちゃって、最近パソコンを使いまくっているよ。", "Having gotten hooked by games, I do nothing but use the computer lately.", "pre", "0.5", "1", "waiting", "ゲームにはまっちゃって、さいきんパソコンをつかいまくっているよ。"});
        preLoadedCards.add(new String[]{"アメリカにいた時はコーラを飲みまくっていた。", "When I was in the US, I drank coke like all the time.", "pre", "0.5", "1", "waiting", "アメリカにいたときはコーラをのみまくっていた。"});
        preLoadedCards.add(new String[]{"このままで宜しいですか？", "Is it ok just like this?", "pre", "0.5", "1", "waiting", "このままでよろしいですか？"});
        preLoadedCards.add(new String[]{"半分しか食べてないままで捨てちゃダメ！", "You can't throw it out leaving it in that half-eaten condition!", "pre", "0.5", "1", "waiting", "はんぶんしかたべてないままですてちゃダメ！"});
        preLoadedCards.add(new String[]{"今日だけは悲しいままでいさせてほしい。", "For only today, I want you to let me stay in this sad condition.", "pre", "0.5", "1", "waiting", "きょうだけはかなしいままでいさせてほしい。"});
        preLoadedCards.add(new String[]{"その格好のままでクラブに入れないよ。", "You can't get in the club in that getup (without changing it).", "pre", "0.5", "1", "waiting", "そのかっこうのままでクラブにはいれないよ。"});
        preLoadedCards.add(new String[]{"テレビを付けっぱなしにしなければ眠れない人は、結構いる。", "There exists a fair number of people who cannot sleep unless they turn on the TV and leave it that way.", "pre", "0.5", "1", "waiting", "テレビをつけっぱなしにしなければねむれないひとは、けっこういる。"});
        preLoadedCards.add(new String[]{"窓が開けっ放しだったので、蚊がいっぱい入った。", "The window was left wide open so a lot of mosquitoes got in.", "pre", "0.5", "1", "waiting", "まどがあけっばなしだったので、かがいっぱいはいった。"});
        preLoadedCards.add(new String[]{"吾輩は猫である。", "I am a cat.", "pre", "0.5", "1", "waiting", "わがはいはねこである。"});
        preLoadedCards.add(new String[]{"混合物とは、２種類以上の純物質が混じりあっている物質である。", "An amalgam is a mixture of two or more pure substances.", "pre", "0.5", "1", "waiting", "こんごうぶつとは、２しゅるいいじょうのじゅんぶっしつがまじりあっているぶっしつである。"});
        preLoadedCards.add(new String[]{"国土交通省は２年後に利用率を７０％まで引き上げる考えで、買い替え時に利用する気になるかどうかがカギになりそうだ。", "With the idea of raising percentage of usage to 70% in two years, it seems likely that the key will become whether the Ministry of Land, Infrastructure, and Transport will employ [it] when it buys replacements.", "pre", "0.5", "1", "waiting", "こくどこうつうしょうは２ねんごにりようりつを７０％までひきあげるかんがえで、かいかえときにりようするきになるかどうかがカギになりそうだ。"});
        preLoadedCards.add(new String[]{"これは不公平ではないでしょうか。", "Wouldn't you consider this to be unfair?", "pre", "0.5", "1", "waiting", "これはふこうへいではないでしょうか。"});
        preLoadedCards.add(new String[]{"言語は簡単にマスターできることではない。", "Language is not something that can be mastered easily.", "pre", "0.5", "1", "waiting", "げんごはかんたんにマスターできることではない。"});
        preLoadedCards.add(new String[]{"花火は、火薬と金属の粉末を混ぜたものに火を付け、燃焼時の火花を楽しむためのもの。", "Fireworks are for the enjoyment of sparks created from combustion created by lighting up a mixture of gunpowder and metal powder.", "pre", "0.5", "1", "waiting", "はなびは、かやくときんぞくのふんまつをまぜたものにひをづけ、ねんしょうときのひばなをたのしむためのもの。"});
        preLoadedCards.add(new String[]{"企業内の顧客データを利用し、彼の行方を調べることが出来た。", "Was able to investigate his whereabouts using the company's internal customer data.", "pre", "0.5", "1", "waiting", "きぎょうないのこきゃくデータをりようし、かのなめがたをしらべることができた。"});
        preLoadedCards.add(new String[]{"封筒には写真が数枚入っており、手紙が添えられていた。", "Several pictures were inside the envelope, and a letter was attached.", "pre", "0.5", "1", "waiting", "ふうとうにはしゃしんがすうまいいっっており、てがみがそえられていた。"});
        preLoadedCards.add(new String[]{"この旅館は、様々な新しい設備が備えており、とても快適だった。", "This Japanese inn having been equipped with various new facilities, was very comfortable.", "pre", "0.5", "1", "waiting", "このりょかんは、さまざまなあたらしいせつびがそなえており、とてもかいてきだった。"});
        preLoadedCards.add(new String[]{"彼は漫画マニアだから、これらをもう全部読んだはずだよ。", "He's has a mania for comic book so I expect he read all these already.", "pre", "0.5", "1", "waiting", "かれはまんがマニアだから、これらをもうぜんぶよんだはずだよ。"});
        preLoadedCards.add(new String[]{"この料理はおいしいはずだったが、焦げちゃって、まずくなった。", "This dish was expected to be tasty but it burned and became distasteful.", "pre", "0.5", "1", "waiting", "このりょうりはおいしいはずだったが、こげちゃって、まずくなった。"});
        preLoadedCards.add(new String[]{"色々予定してあるから、今年は楽しいクリスマスのはず。", "Because various things have been planned out, I expect a fun Christmas this year.", "pre", "0.5", "1", "waiting", "いろいろよていしてあるから、こんねんはたのしいクリスマスのはず。"});
        preLoadedCards.add(new String[]{"そう簡単に直せるはずがないよ。", "It's not supposed to be that easy to fix.", "pre", "0.5", "1", "waiting", "そうかんたんになおせるはずがないよ。"});
        preLoadedCards.add(new String[]{"打合せは毎週２時から始まるはずじゃないですか？", "This meeting is supposed to start every week at 2 o'clock, isn't it?", "pre", "0.5", "1", "waiting", "うちあわせはまいしゅう２ときからはじまるはずじゃないですか？"});
        preLoadedCards.add(new String[]{"何かを買う前に本当に必要かどうかをよく考えるべきだ。", "Before buying something, one should think well on whether it's really necessary or not.", "pre", "0.5", "1", "waiting", "なにかをかうまえにほんとうにひつようかどうかをよくかんがえるべきだ。"});
        preLoadedCards.add(new String[]{"例え国のためであっても、国民を騙すべきではないと思う。", "Even if it is, for example, for the country, I don't think the country's citizens should be deceived.", "pre", "0.5", "1", "waiting", "たとえくにのためであっても、こくみんをだますべきではないとおもう。"});
        preLoadedCards.add(new String[]{"預金者が大手銀行を相手取って訴訟を起こすケースも出ており、金融庁は被害者の救済を優先させて、金融機関に犯罪防止対策の強化を促すべきだと判断。", "With cases coming out of depositors suing large banks, the Financial Services Agency decided it should prioritize relief for victims and urge banks to strengthen measures for crime prevention.", "pre", "0.5", "1", "waiting", "よきんものがおおてぎんこうをあいてとってそしょうをおこすケースもでており、きんゆうちょうはひがいしゃのきゅうさいをゆうせんさせて、きんゆうきかんにはんざいぼうしたいさくのきょうかをうながすべきだとはんだん。"});
        preLoadedCards.add(new String[]{"早く帰るべき。", "Should go home early.", "pre", "0.5", "1", "waiting", "はやくかえるべき。"});
        preLoadedCards.add(new String[]{"早く帰るべく、準備をし始めた。", "In trying to go home early, started the preparations.", "pre", "0.5", "1", "waiting", "はやくかえるべく、じゅんびをしはじめた。"});
        preLoadedCards.add(new String[]{"試験に合格すべく、皆一生懸命に勉強している。", "Everybody is studying very hard in an effort to pass the exam.", "pre", "0.5", "1", "waiting", "しけんにごうかくすべく、みないっしょうけんめいにべんきょうしている。"});
        preLoadedCards.add(new String[]{"今後もお客様との対話の窓口として、より充実していくべく努力してまいります。", "We are working from here in an effort to provide a enriched window for customer interaction.", "pre", "0.5", "1", "waiting", "こんごもおきゃくさまとのたいわのまどぐちとして、よりじゅうじつしていくべくどりょくしてまいります。"});
        preLoadedCards.add(new String[]{"ゴミ捨てるべからず。", "You must not throw away trash.", "pre", "0.5", "1", "waiting", "ゴミすてるべからず。"});
        preLoadedCards.add(new String[]{"安全措置を忘れるべからず。", "You must not forget the safety measures.", "pre", "0.5", "1", "waiting", "あんぜんそちをわすれるべからず。"});
        preLoadedCards.add(new String[]{"宿題が多すぎて、トイレに行く時間さえなかった。", "There was so much homework, I didn't even have time to go to the bathroom.", "pre", "0.5", "1", "waiting", "しゅくだいがおおすぎて、トイレにいくじかんさえなかった。"});
        preLoadedCards.add(new String[]{"お金さえあれば、何でも出来るよ。", "The least you need is money and you can do anything.", "pre", "0.5", "1", "waiting", "おきんさえあれば、なんでもできるよ。"});
        preLoadedCards.add(new String[]{"お弁当を買うお金さえなかった。", "I didn't even have money to buy lunch.", "pre", "0.5", "1", "waiting", "おべんとうをかうおきんさえなかった。"});
        preLoadedCards.add(new String[]{"私でさえ出来れば、あんたには楽ちんでしょう。", "If even I can do it, it should be a breeze for you.", "pre", "0.5", "1", "waiting", "わたしでさえできれば、あんたにはらくちんでしょう。"});
        preLoadedCards.add(new String[]{"ビタミンを食べさえすれば、健康が保証されますよ。", "If you just eat vitamins, your health will be guaranteed.", "pre", "0.5", "1", "waiting", "ビタミンをたべさえすれば、けんこうがほしょうされますよ。"});
        preLoadedCards.add(new String[]{"自分の過ちを認めさえしなければ、問題は解決しないよ。", "The problem won't be solved if you don't even recognize your own mistake, you know.", "pre", "0.5", "1", "waiting", "じぶんのあやまちをみとめさえしなければ、もんだいはかいけつしないよ。"});
        preLoadedCards.add(new String[]{"この天才の私ですらわからなかった。", "Even a genius such as myself couldn't solve it.", "pre", "0.5", "1", "waiting", "このてんさいのわたしですらわからなかった。"});
        preLoadedCards.add(new String[]{"私は緊張しすぎて、ちらっと見ることすら出来ませんでした。", "I was so nervous that I couldn't even take a quick peek.", "pre", "0.5", "1", "waiting", "わたしはきんちょうしすぎて、ちらっとみることすらできませんでした。"});
        preLoadedCards.add(new String[]{"「人」の漢字すら知らない生徒は、いないでしょ！", "There are no students that don't even know the 「人」 kanji!", "pre", "0.5", "1", "waiting", "「にん」のかんじすらしらないせいとは、いないでしょ！"});
        preLoadedCards.add(new String[]{"漢字はおろか、ひらがなさえ読めないよ！", "Forget about kanji, I can't even read hiragana!", "pre", "0.5", "1", "waiting", "かんじはおろか、ひらがなさえよめないよ！"});
        preLoadedCards.add(new String[]{"結婚はおろか、2ヶ月付き合って、結局別れてしまった。", "We eventually broke up after going out two months much less get married.", "pre", "0.5", "1", "waiting", "けっこんはおろか、2がつつきあって、けっきょくわかれてしまった。"});
        preLoadedCards.add(new String[]{"大学はおろか、高校すら卒業しなかった。", "I didn't even graduate from high school much less college.", "pre", "0.5", "1", "waiting", "だいがくはおろか、こうこうすらそつぎょうしなかった。"});
        preLoadedCards.add(new String[]{"早くきてよ！何を恥ずかしがっているの？", "Hurry up and come here. What are you acting all embarrassed for?", "pre", "0.5", "1", "waiting", "はやくきてよ！なにをはずかしがっているの？"});
        preLoadedCards.add(new String[]{"彼女は朝早く起こされるのを嫌がるタイプです。", "My girlfriend is the type to show dislike towards getting woken up early in the morning.", "pre", "0.5", "1", "waiting", "かのじょはあさはやくおこされるのをいやがるタイプです。"});
        preLoadedCards.add(new String[]{"うちの子供はプールに入るのを理由もなく怖がる。", "Our child acts afraid about entering a pool without any reason.", "pre", "0.5", "1", "waiting", "うちのこどもはプールにいるのをりゆうもなくこわがる。"});
        preLoadedCards.add(new String[]{"家に帰ったら、すぐパソコンを使いたがる。", "[He] soon acts like wanting to use computer as soon as [he] gets home.", "pre", "0.5", "1", "waiting", "いえにかえったら、すぐパソコンをつかいたがる。"});
        preLoadedCards.add(new String[]{"みんなイタリアに行きたがってるんだけど、私の予算で行けるかどうかはとても怪しい。", "Everybody is acting like they want to go to Italy but it's suspicious whether I can go or not going by my budget.", "pre", "0.5", "1", "waiting", "みんなイタリアにいきたがってるんだけど、わたしのよさんでいけるかどうかはとてもあやしい。"});
        preLoadedCards.add(new String[]{"妻はルイヴィトンのバッグを欲しがっているんだけど、そんなもん、買えるわけないでしょう！", "My wife was showing signs of wanting a Louis Vuitton bag but there's no way I can buy something like that!", "pre", "0.5", "1", "waiting", "つまはルイヴィトンのバッグをほしがっているんだけど、そんなもん、かえるわけないでしょう！"});
        preLoadedCards.add(new String[]{"私は寒がり屋だから、ミネソタで暮らすのは辛かった。", "I'm the type who easily gets cold and so living in Minnesota was painful.", "pre", "0.5", "1", "waiting", "わたしはさむがりやだから、ミネソタでくらすのはつらかった。"});
        preLoadedCards.add(new String[]{"ボールは爆発せんばかりに、膨らんでいた。", "The ball was expanding as if it was going to explode.", "pre", "0.5", "1", "waiting", "ボールはばくはつせんばかりに、ふくらんでいた。"});
        preLoadedCards.add(new String[]{"「あんたと関係ない」と言わんばかりに彼女は彼を無視していた。", "She ignored him as if to say, You have nothing to do with this.", "pre", "0.5", "1", "waiting", "「あんたとかんけいない」といわんばかりにかのじょはかれをむししていた。"});
        preLoadedCards.add(new String[]{"昨日のケンカで何も言わなかったばかりに、平気な顔をしている。", "Has a calm face as if [he] didn't say anything during the fight yesterday.", "pre", "0.5", "1", "waiting", "きのうのケンカでなにもいわなかったばかりに、へいきなかおをしている。"});
        preLoadedCards.add(new String[]{"紅葉が始まり、すっかり秋めいた空気になってきた。", "With the leaves starting to change color, the air came to become quite autumn like.", "pre", "0.5", "1", "waiting", "こうようがはじまり、すっかりあきめいたくうきになってきた。"});
        preLoadedCards.add(new String[]{"そんな謎めいた顔をされても、うまく説明できないよ。", "Even having that kind of puzzled look done to me, I can't explain it very well, you know.", "pre", "0.5", "1", "waiting", "そんななぞめいたかおをされても、うまくせつめいできないよ。"});
        preLoadedCards.add(new String[]{"いつも皮肉めいた言い方をしたら、みんなを嫌がらせるよ。", "You'll make everyone dislike you if you keep speaking with that ironic tone, you know.", "pre", "0.5", "1", "waiting", "いつもひにくめいたいいかたをしたら、みんなをいやがらせるよ。"});
        preLoadedCards.add(new String[]{"このテレビがこれ以上壊れたら、新しいのを買わざるを得ないな。", "If this TV breaks even more, there's no choice but to buy a new one.", "pre", "0.5", "1", "waiting", "このテレビがこれいじょうこわれたら、あたらしいのをかわざるをえないな。"});
        preLoadedCards.add(new String[]{"ずっと我慢してきたが、この状態だと歯医者さんに行かざるを得ない。", "I tolerated it all this time but in this situation, I can't not go to the dentist.", "pre", "0.5", "1", "waiting", "ずっとがまんしてきたが、このじょうたいだとはいしゃさんにいかざるをえない。"});
        preLoadedCards.add(new String[]{"上司の話を聞くと、どうしても海外に出張をせざるを得ないようです。", "Hearing the story from the boss, it seems like I can't not go on a business trip overseas no matter what.", "pre", "0.5", "1", "waiting", "じょうしのはなしをきくと、どうしてもかいがいにしゅっちょうをせざるをえないようです。"});
        preLoadedCards.add(new String[]{"やむを得ない事由により手続が遅れた場合、必ずご連絡下さい。", "If the paperwork should be late due to uncontrollable circumstance, please make sure to contact us.", "pre", "0.5", "1", "waiting", "やむをえないじゆうによりてつづきがおくれたばあい、かならずごれんらくください。"});
        preLoadedCards.add(new String[]{"この仕事は厳しいかもしれませんが、最近の不景気では新しい仕事が見つからないのでやむを得ない状態です。", "This job may be bad but with the recent economic downturn, it's a situation where nothing can be done.", "pre", "0.5", "1", "waiting", "このしごとはきびしいかもしれませんが、さいきんのふけいきではあたらしいしごとがみつからないのでやむをえないじょうたいです。"});
        preLoadedCards.add(new String[]{"この場ではちょっと決めかねますので、また別途会議を設けましょう。", "Since making a decision here is impossible, let's set up a separate meeting again.", "pre", "0.5", "1", "waiting", "このばではちょっときめかねますので、またべっとかいぎをもうけましょう。"});
        preLoadedCards.add(new String[]{"このままでは、個人情報が漏洩しかねないので、速やかに対応をお願い致します。", "At this rate, there is a possibility that personal information might leak so I request that this be dealt with promptly.", "pre", "0.5", "1", "waiting", "このままでは、こじんじょうほうがろうえいしかねないので、すみやかにたいおうをおねがいいたします。"});
        preLoadedCards.add(new String[]{"確定申告は忘れがちな手続のひとつだ。", "Filing income taxes is one of those processes that one is apt to forget.", "pre", "0.5", "1", "waiting", "かくていしんこくはわすれがちなてつづきのひとつだ。"});
        preLoadedCards.add(new String[]{"留守がちなご家庭には、犬よりも、猫の方がおすすめです。", "For families that tend to be away from home, cats are recommended over dogs.", "pre", "0.5", "1", "waiting", "るすがちなごかていには、いぬよりも、ねこのほうがおすすめです。"});
        preLoadedCards.add(new String[]{"父親は病気がちで、みんなが心配している。", "Father is prone to illness and everybody is worried.", "pre", "0.5", "1", "waiting", "ちちおやはびょうきがちで、みんながしんぱいしている。"});
        preLoadedCards.add(new String[]{"テレビを見ながら、寝ちゃダメよ！", "Don't watch TV while sleeping!", "pre", "0.5", "1", "waiting", "テレビをみながら、ねちゃダメよ！"});
        preLoadedCards.add(new String[]{"二日酔いで痛む頭を押さえつつ、トイレに入った。", "Went into the bathroom while holding an aching head from a hangover.", "pre", "0.5", "1", "waiting", "ふつかよいでいたむあたまをおさえつつ、トイレにいっった。"});
        preLoadedCards.add(new String[]{"体によくないと思いつつ、最近は全然運動してない。", "While thinking it's bad for body, haven't exercised at all recently.", "pre", "0.5", "1", "waiting", "からだによくないとおもいつつ、さいきんはぜんぜんうんどうしてない。"});
        preLoadedCards.add(new String[]{"電気製品の発展につれて、ハードディスクの容量はますます大きくなりつつある。", "With the development of electronic goods, hard disk drive capacities are becoming ever larger.", "pre", "0.5", "1", "waiting", "でんきせいひんのはってんにつれて、ハードディスクのようりょうはますますおおきくなりつつある。"});
        preLoadedCards.add(new String[]{"今の日本では、終身雇用や年功序列という雇用慣行が崩れつつある。", "In today's Japan, hiring practices like life-time employment and age-based ranking are tending to break down.", "pre", "0.5", "1", "waiting", "いまのにっぽんでは、しゅうしんこようやねんこうじょれつというこようかんこうがくずれつつある。"});
        preLoadedCards.add(new String[]{"多くの大学生は、締切日ぎりぎりまで、宿題をやらないきらいがある。", "A lot of college students have a bad tendency of not doing their homework until just barely it's due date.", "pre", "0.5", "1", "waiting", "おおくのだいがくせいは、しめきりびぎりぎりまで、しゅくだいをやらないきらいがある。"});
        preLoadedCards.add(new String[]{"コーディングが好きな開発者は、ちゃんとしたドキュメント作成と十分なテストを怠るきらいがある。", "Developers that like coding have a bad tendency to neglect proper documents and adequate testing.", "pre", "0.5", "1", "waiting", "コーディングがすきなかいはつしゃは、ちゃんとしたドキュメントさくせいとじゅうぶんなテストをおこたるきらいがある。"});
        preLoadedCards.add(new String[]{"相手は剣の達人だ。そう簡単には勝てまい。", "Your opponent is a master of the sword. I doubt you can win so easily.", "pre", "0.5", "1", "waiting", "あいてはつるぎのたつじんだ。そうかんたんにはかてまい。"});
        preLoadedCards.add(new String[]{"そんな無茶な手段は認めますまい！", "I won't approve of such an unreasonable method!", "pre", "0.5", "1", "waiting", "そんなむちゃなしゅだんはみとめますまい！"});
        preLoadedCards.add(new String[]{"その時までは決して彼に会うまいと心に決めていた。", "Until that time, I had decided in my heart to not meet him by any means.", "pre", "0.5", "1", "waiting", "そのときまではけっしてかれにあうまいとこころにきめていた。"});
        preLoadedCards.add(new String[]{"あの人は、二度と嘘をつくまいと誓ったのです。", "That person had sworn to never lie again.", "pre", "0.5", "1", "waiting", "あのにんは、にどとうそをつくまいとちかったのです。"});
        preLoadedCards.add(new String[]{"明日に行くのをやめよう。", "Let's not go tomorrow. (lit: Let's quit going tomorrow.)", "pre", "0.5", "1", "waiting", "あしたにいくのをやめよう。"});
        preLoadedCards.add(new String[]{"肉を食べないようにしている。", "Trying not to eat meat.", "pre", "0.5", "1", "waiting", "にくをたべないようにしている。"});
        preLoadedCards.add(new String[]{"あいつが大学に入ろうが入るまいが、俺とは関係ないよ。", "Whether that guy is going to college or not, it has nothing to do with me.", "pre", "0.5", "1", "waiting", "あいつがだいがくにいろうがいるまいが、おれとはかんけいないよ。"});
        preLoadedCards.add(new String[]{"時間があろうがあるまいが、間に合わせるしかない。", "Whether there is time or not, there's nothing to do but make it on time.", "pre", "0.5", "1", "waiting", "じかんがあろうがあるまいが、まにあわせるしかない。"});
        preLoadedCards.add(new String[]{"最近のウィルスは強力で、プログラムを実行しようがしまいが、ページを見るだけで感染するらしい。", "The viruses lately have been strong and whether you run a program or not, I hear it will spread just by looking at the page.", "pre", "0.5", "1", "waiting", "さいきんのウィルスはきょうりょくで、プログラムをじっこうしようがしまいが、ページをみるだけでかんせんするらしい。"});
        preLoadedCards.add(new String[]{"今後50年、人間が直面するであろう問題に正面から向き合って、自ら解決をはかりつつ、そのノウハウが次の産業となるシナリオを考えたい。", "I would like to directly approach problems that humans have likely encounter the next 50 years and while measuring solutions, take that knowledge and think about scenarios that will become the next industry.", "pre", "0.5", "1", "waiting", "こんご50ねん、にんげんがちょくめんするであろうもんだいにしょうめんからむきあって、みずからかいけつをはかりつつ、そのノウハウがつぎのさんぎょうとなるシナリオをかんがえたい。"});
        preLoadedCards.add(new String[]{"もちろん、生徒数減少の現在、学科の新設は困難であろうが、職業科の統廃合や科内コースの改編などで時代に合わせた変革が求められているはずである。", "Of course, new educational facilities will likely be difficult with this period of decreasing student population but with reorganizations of subjects and courses within subjects, there is supposed to be demand for reform fit for this period.", "pre", "0.5", "1", "waiting", "もちろん、せいとすうげんしょうのげんざい、がっかのしんせつはこんなんであろうが、しょくぎょうかのとうはいごうやかないコースのかいへんなどでじだいにあわせたへんかくがもとめられているはずである。"});
        preLoadedCards.add(new String[]{"どんな商品でもネットで販売するだけで売上が伸びるというものではなかろう。", "It's not necessarily the case that sales go up just by selling any type of product on the net.", "pre", "0.5", "1", "waiting", "どんなしょうひんでもネットではんばいするだけでうりあがのびるというものではなかろう。"});
        preLoadedCards.add(new String[]{"運動を始めるのが早かろうが遅かろうが、健康にいいというのは変わりません。", "Whether you start exercising early or late, the fact that it's good for you health doesn't change.", "pre", "0.5", "1", "waiting", "うんどうをはじめるのがはやかろうがおそかろうが、けんこうにいいというのはかわりません。"});
        preLoadedCards.add(new String[]{"休日であろうが、なかろうが、この仕事では関係ないみたい。", "Whether it's a holiday or not, it looks like it doesn't matter for this job.", "pre", "0.5", "1", "waiting", "きゅうじつであろうが、なかろうが、このしごとではかんけいないみたい。"});
        preLoadedCards.add(new String[]{"このドキュメントは間違えだらけで、全然役に立たない。", "This document is just riddled with mistakes and is not useful at all.", "pre", "0.5", "1", "waiting", "このドキュメントはまちがえだらけで、ぜんぜんやくにたたない。"});
        preLoadedCards.add(new String[]{"携帯を２年間使ってたら、傷だらけになった。", "After using cell phone for 2 years, it became covered with scratches.", "pre", "0.5", "1", "waiting", "けいたいを２ねんかんつかってたら、きずだらけになった。"});
        preLoadedCards.add(new String[]{"この埃だれけのテレビをちゃんと拭いてくれない？", "Can you properly dust this TV completely covered in dust?", "pre", "0.5", "1", "waiting", "このほこりだれけのテレビをちゃんとぬぐいてくれない？"});
        preLoadedCards.add(new String[]{"彼は油まみれになりながら、車の修理に頑張りました。", "While becoming covered in oil, he worked hard at fixing the car.", "pre", "0.5", "1", "waiting", "かれはあぶらまみれになりながら、くるまのしゅうりにがんばりました。"});
        preLoadedCards.add(new String[]{"たった１キロを走っただけで、汗まみれになるのは情けない。", "It's pitiful that one gets covered in sweat from running just 1 kilometer.", "pre", "0.5", "1", "waiting", "たった１キロをはしっただけで、あせまみれになるのはなさけない。"});
        preLoadedCards.add(new String[]{"白ずくめ団体は去年ニューズになっていた。", "The organization dressed all in white was on the news last year.", "pre", "0.5", "1", "waiting", "しろずくめだんたいはきょねんニューズになっていた。"});
        preLoadedCards.add(new String[]{"女の子と共通の話題ができて、自分の体も健康になる。いいことずくめですよ。", "A common topic to talk about with girls is able to be made and one's own body also becomes healthy. It's all good things.", "pre", "0.5", "1", "waiting", "おんなのこときょうつうのわだいができて、じぶんのからだもけんこうになる。いいことずくめですよ。"});
        preLoadedCards.add(new String[]{"彼女は、教授の姿を見るが早いか、教室から逃げ出した。", "The instant [she] saw the teacher's figure, [she] ran away from the classroom.", "pre", "0.5", "1", "waiting", "かのじょは、きょうじゅのすがたをみるがはやいか、きょうしつからにげだした。"});
        preLoadedCards.add(new String[]{"「食べてみよう」と言うが早いか、口の中に放り込んだ。", "The instant [he] said let's try eating it, he threw [it] into his mouth.", "pre", "0.5", "1", "waiting", "「たべてみよう」というがはやいか、くちのなかにほうりこんだ。"});
        preLoadedCards.add(new String[]{"「食べてみよう」と言ったが早いか、口の中に放り込んだ。", "The instant [he] said let's try eating it, he threw [it] into his mouth.", "pre", "0.5", "1", "waiting", "「たべてみよう」といっったがはやいか、くちのなかにほうりこんだ。"});
        preLoadedCards.add(new String[]{"私の顔を見るや、何か言おうとした。", "[He] tried to say something as soon as he saw my face.", "pre", "0.5", "1", "waiting", "わたしのかおをみるや、なにかいおうとした。"});
        preLoadedCards.add(new String[]{"搭乗のアナウンスが聞こえるや否や、みんながゲートの方へ走り出した。", "As soon as the announcement to board was audible, everybody started running toward the gate.", "pre", "0.5", "1", "waiting", "とうじょうのアナウンスがきこえるやひや、みんながゲートのほうへはしりだした。"});
        preLoadedCards.add(new String[]{"子供が掃除するそばから散らかすから、もうあきらめたくなった。", "The child messes up [the room] (repeatedly) as soon as I clean so I already became wanting to give up.", "pre", "0.5", "1", "waiting", "こどもがそうじするそばからちらかすから、もうあきらめたくなった。"});
        preLoadedCards.add(new String[]{"教科書を読んだそばから忘れてしまうので勉強ができない。", "Forget (repeatedly) right after I read the textbook so I can't study.", "pre", "0.5", "1", "waiting", "きょうかしょをよんだそばからわすれてしまうのでべんきょうができない。"});
        preLoadedCards.add(new String[]{"昼間だから絶対込んでいると思いきや、一人もいなかった。", "Despite having thought that it must be crowded since it was afternoon, (surprisingly) not a single person was there.", "pre", "0.5", "1", "waiting", "ひるまだからぜったいこんでいるとおもいきや、ひとりもいなかった。"});
        preLoadedCards.add(new String[]{"このレストランは安いと思いきや、会計は5千円以上だった！", "Thought this restaurant would be cheap but (surprisingly) the bill was over 5,000 yen!", "pre", "0.5", "1", "waiting", "このレストランはやすいとおもいきや、かいけいは5せんえんいじょうだった！"});
        preLoadedCards.add(new String[]{"散歩がてら、タバコを買いに行きました。", "While taking a stroll, I also used that time to buy cigarettes.", "pre", "0.5", "1", "waiting", "さんぽがてら、タバコをかいにいきました。"});
        preLoadedCards.add(new String[]{"博物館を見がてらに、お土産を買うつもりです。", "While seeing the museum, I plan to also use that time to buy souvenirs.", "pre", "0.5", "1", "waiting", "はくぶつかんをけんがてらに、おみやげをかうつもりです。"});
        preLoadedCards.add(new String[]{"事情を2時間かけて説明をしたあげく、納得してもらえなかった。", "[After a great deal of] explaining the circumstances for 2 hours, [in the end], couldn't receive understanding.", "pre", "0.5", "1", "waiting", "じじょうを2じかんかけてせつめいをしたあげく、なっとくしてもらえなかった。"});
        preLoadedCards.add(new String[]{"先生と相談のあげく、退学することにした。", "[After much] consulting with teacher, [in the end], decided on dropping out of school.", "pre", "0.5", "1", "waiting", "せんせいとそうだんのあげく、たいがくすることにした。"});

    }

}