package com.edros.app;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    final int BLUE       = Color.parseColor("#2563EB");
    final int BLUE_DARK  = Color.parseColor("#1E40AF");
    final int DARK       = Color.parseColor("#111827");
    final int GRAY       = Color.parseColor("#6B7280");
    final int LIGHT      = Color.parseColor("#F3F6FA");
    final int WHITE      = Color.WHITE;
    final int GREEN      = Color.parseColor("#10B981");
    final int ORANGE     = Color.parseColor("#F59E0B");
    final int PURPLE     = Color.parseColor("#8B5CF6");

    LinearLayout root;
    LinearLayout content;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("edros", MODE_PRIVATE);
        showHome();
    }

    int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density);
    }

    TextView tv(String text, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.RIGHT);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    CardView card() {
        CardView c = new CardView(this);
        c.setRadius(dp(16));
        c.setCardElevation(dp(3));
        c.setCardBackgroundColor(WHITE);
        c.setUseCompatPadding(true);
        return c;
    }

    LinearLayout.LayoutParams lpFull(int h) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, h);
        p.setMargins(0, dp(6), 0, dp(6));
        return p;
    }

    void base(String header) {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(LIGHT);

        LinearLayout headerBox = new LinearLayout(this);
        headerBox.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable g = new GradientDrawable(
                GradientDrawable.Orientation.TR_TL,
                new int[]{BLUE, BLUE_DARK});
        g.setCornerRadii(new float[]{0,0,0,0,dp(24),dp(24),dp(24),dp(24)});
        headerBox.setBackground(g);
        headerBox.setPadding(dp(20), dp(28), dp(20), dp(24));

        TextView title = tv(header, 24, WHITE, true);
        headerBox.addView(title);

        TextView sub = tv("تعلّم بذكاء، ذاكر باحتراف", 13, Color.parseColor("#DBEAFE"), false);
        sub.setPadding(0, dp(4), 0, 0);
        headerBox.addView(sub);

        root.addView(headerBox, new LinearLayout.LayoutParams(-1, -2));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(16), dp(16), dp(16));
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        setContentView(root);
    }

    void nav(String active) {
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setBackgroundColor(WHITE);
        bar.setPadding(dp(4), dp(8), dp(4), dp(8));
        bar.setElevation(dp(12));

        String[][] items = {
                {"🏠", "الرئيسية", "home"},
                {"📚", "المواد", "subjects"},
                {"📝", "الامتحانات", "exams"},
                {"📈", "تقدمي", "progress"}
        };

        for (String[] it : items) {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);

            TextView icon = tv(it[0], 20, it[2].equals(active) ? BLUE : GRAY, false);
            icon.setGravity(Gravity.CENTER);
            TextView label = tv(it[1], 11, it[2].equals(active) ? BLUE : GRAY, it[2].equals(active));

            item.addView(icon);
            item.addView(label);

            final String key = it[2];
            item.setOnClickListener(v -> {
                if (key.equals("home")) showHome();
                else if (key.equals("subjects")) showSubjects();
                else if (key.equals("exams")) showExams();
                else showProgress();
            });

            bar.addView(item, new LinearLayout.LayoutParams(0, -2, 1));
        }
        root.addView(bar, new LinearLayout.LayoutParams(-1, -2));
    }

    void actionCard(String emoji, String title, String subtitle, int color, View.OnClickListener click) {
        CardView c = card();
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(16), dp(16), dp(16));

        TextView ico = tv(emoji, 22, WHITE, false);
        ico.setGravity(Gravity.CENTER);
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(color);
        ico.setBackground(circle);
        row.addView(ico, new LinearLayout.LayoutParams(dp(48), dp(48)));

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);
        texts.setPadding(dp(14), 0, 0, 0);
        TextView t1 = tv(title, 16, DARK, true);
        TextView t2 = tv(subtitle, 13, GRAY, false);
        t2.setPadding(0, dp(2), 0, 0);
        texts.addView(t1);
        texts.addView(t2);
        row.addView(texts, new LinearLayout.LayoutParams(0, -2, 1));

        TextView arrow = tv("‹", 22, GRAY, true);
        arrow.setGravity(Gravity.CENTER);
        row.addView(arrow);

        c.addView(row);
        c.setOnClickListener(click);
        content.addView(c, lpFull(-2));
    }

    void showHome() {
        base("إدرس");

        TextView hello = tv("أهلاً بك 👋", 22, DARK, true);
        content.addView(hello);

        TextView subtitle = tv("ابدأ رحلتك التعليمية الآن، وذاكر بذكاء.", 14, GRAY, false);
        subtitle.setPadding(0, dp(4), 0, dp(16));
        content.addView(subtitle);

        actionCard("📚", "المواد الدراسية", "تصفح كل موادك وابدأ المذاكرة", BLUE, v -> showSubjects());
        actionCard("🤖", "مساعد إدرس الذكي", "اسأل أي سؤال واحصل على شرح", PURPLE, v -> showAI());
        actionCard("📝", "الامتحانات", "اختبر نفسك وتأكد من مستواك", ORANGE, v -> showExams());
        actionCard("📈", "تقدمي", "تابع إنجازاتك ونقاطك", GREEN, v -> showProgress());

        nav("home");
    }

    void showSubjects() {
        base("المواد الدراسية");

        String[][] subjects = {
                {"📗", "اللغة العربية"},
                {"📘", "اللغة الإنجليزية"},
                {"📐", "الرياضيات"},
                {"🔬", "العلوم"},
                {"🌍", "الدراسات الاجتماعية"},
                {"🇫🇷", "اللغة الفرنسية"}
        };

        for (String[] s : subjects) {
            final String subject = s[1];
            CardView c = card();
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(dp(16), dp(16), dp(16), dp(16));

            TextView emoji = tv(s[0], 26, DARK, false);
            emoji.setGravity(Gravity.CENTER);
            row.addView(emoji, new LinearLayout.LayoutParams(dp(44), dp(44)));

            LinearLayout texts = new LinearLayout(this);
            texts.setOrientation(LinearLayout.VERTICAL);
            texts.setPadding(dp(14), 0, 0, 0);
            TextView t1 = tv(subject, 16, DARK, true);
            TextView t2 = tv("اضغط لبدء المذاكرة", 12, GRAY, false);
            t2.setPadding(0, dp(2), 0, 0);
            texts.addView(t1);
            texts.addView(t2);
            row.addView(texts, new LinearLayout.LayoutParams(0, -2, 1));

            row.addView(tv("‹", 22, GRAY, true));
            c.addView(row);
            c.setOnClickListener(v -> showLesson(subject));
            content.addView(c, lpFull(-2));
        }

        nav("subjects");
    }

    void showLesson(String subject) {
        base(subject);

        TextView t = tv("اختر ما تريد دراسته", 18, DARK, true);
        t.setPadding(0, 0, 0, dp(12));
        content.addView(t);

        actionCard("📖", "شرح الدرس", "شرح مبسط ومنظم", BLUE,
                v -> Toast.makeText(this, "سيتم إضافة شروحات " + subject + " قريبًا", Toast.LENGTH_SHORT).show());

        actionCard("✏️", "أسئلة وتدريبات", "تدرب على ما تعلمته", ORANGE,
                v -> Toast.makeText(this, "قريبًا تمارين " + subject, Toast.LENGTH_SHORT).show());

        actionCard("🎯", "اختبار قصير", "اختبر نفسك واحصل على نقاط", GREEN,
                v -> quiz(subject));

        nav("subjects");
    }

    void showAI() {
        base("مساعد إدرس الذكي");

        LinearLayout chatBox = new LinearLayout(this);
        chatBox.setOrientation(LinearLayout.VERTICAL);
        chatBox.setPadding(0, 0, 0, dp(12));

        addBubble(chatBox, "مرحبًا 👋\nأنا مساعد إدرس الذكي. اكتب سؤالك وسأشرحه لك خطوة بخطوة.", false);

        final EditText input = new EditText(this);
        input.setHint("اكتب سؤالك هنا...");
        input.setGravity(Gravity.RIGHT);
        input.setTextSize(15);
        input.setPadding(dp(16), dp(14), dp(16), dp(14));
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(LIGHT);
        bg.setCornerRadius(dp(14));
        input.setBackground(bg);
        input.setMaxLines(3);

        TextView sendBtn = tv("إرسال ➤", 16, WHITE, true);
        sendBtn.setGravity(Gravity.CENTER);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setColor(BLUE);
        btnBg.setCornerRadius(dp(14));
        sendBtn.setBackground(btnBg);
        sendBtn.setPadding(0, dp(14), 0, dp(14));

        sendBtn.setOnClickListener(v -> {
            String q = input.getText().toString().trim();
            if (q.isEmpty()) {
                input.setError("اكتب سؤالك أولاً");
                return;
            }
            addBubble(chatBox, q, true);
            input.setText("");
            String reply = generateLocalReply(q);
            chatBox.postDelayed(() -> addBubble(chatBox, reply, false), 400);
        });

        content.addView(chatBox, new LinearLayout.LayoutParams(-1, -2));

        LinearLayout inputArea = new LinearLayout(this);
        inputArea.setOrientation(LinearLayout.VERTICAL);
        inputArea.setPadding(0, dp(8), 0, dp(8));
        inputArea.addView(input, lpFull(-2));
        inputArea.addView(sendBtn, lpFull(-2));

        content.addView(inputArea);

        nav("home");
    }

    void addBubble(LinearLayout parent, String text, boolean isUser) {
        CardView c = card();
        c.setCardBackgroundColor(isUser ? BLUE : WHITE);
        TextView t = tv(text, 15, isUser ? WHITE : DARK, false);
        t.setPadding(dp(16), dp(12), dp(16), dp(12));
        t.setLineSpacing(0, 1.15f);
        c.addView(t);

        LinearLayout wrapper = new LinearLayout(this);
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setGravity(isUser ? Gravity.LEFT : Gravity.RIGHT);
        wrapper.addView(c, new LinearLayout.LayoutParams(dp(280), -2));
        wrapper.setPadding(0, dp(4), 0, dp(4));

        parent.addView(wrapper);
    }

    String generateLocalReply(String q) {
        String s = q.toLowerCase();
        if (s.contains("كسور") || s.contains("كسر")) {
            return "الكسور: هي تمثيل لجزء من الكل.\nمثال: الكسر 1/2 يعني نصف الشيء.\nالبسط فوق (عدد الأجزاء) والمقام تحت (كل الأجزاء).";
        }
        if (s.contains("معادلة") || s.contains("معادلات")) {
            return "المعادلة: هي عبارة رياضية تحتوي على مجهول مثل x.\nلحلها: نعزل المجهول في طرف واحد.\nمثال: 2x + 4 = 10 → 2x = 6 → x = 3.";
        }
        if (s.contains("ماء") || s.contains("دورة الماء")) {
            return "دورة الماء:\n1) التبخر من البحار\n2) التكاثف على شكل سحاب\n3) التساقط (مطر)\n4) الجريان السطحي\nوتتكرر الدورة.";
        }
        if (s.contains("فعل") || s.contains("أفعال") || s.contains("اعراب") || s.contains("إعراب")) {
            return "الفعل في العربية ثلاثة أنواع:\n• ماضي: درسَ\n• مضارع: يدرسُ\n• أمر: ادرسْ\nوالإعراب يختلف حسب موقع الفعل في الجملة.";
        }
        return "سؤال جيد! 🤔\n\nشرح مختصر:\n" + q + "\n\nلتفعيل الذكاء الاصطناعي الكامل، سنربط التطبيق بخدمة AI آمنة في التحديث القادم.";
    }

    void showExams() {
        base("الامتحانات");

        TextView t = tv("اختر امتحانًا لتبدأ", 18, DARK, true);
        t.setPadding(0, 0, 0, dp(12));
        content.addView(t);

        actionCard("📐", "الرياضيات", "5 أسئلة — مستوى متوسط", ORANGE, v -> quiz("الرياضيات"));
        actionCard("🔬", "العلوم", "4 أسئلة — مستوى سهل", PURPLE, v -> quiz("العلوم"));
        actionCard("📗", "اللغة العربية", "4 أسئلة — مستوى متوسط", GREEN, v -> quiz("اللغة العربية"));

        nav("exams");
    }

    void quiz(String subject) {
        final String[][] qs;
        if (subject.equals("الرياضيات")) {
            qs = new String[][]{
                    {"ما ناتج 3 × 4 ؟", "12", "14", "16", "18", "12"},
                    {"ما ناتج 25 + 17 ؟", "40", "42", "44", "46", "42"},
                    {"كم ضلعًا للمثلث؟", "2", "3", "4", "5", "3"},
                    {"ما ناتج 100 ÷ 4 ؟", "20", "25", "30", "40", "25"},
                    {"ما هو الجذر التربيعي لـ 81 ؟", "7", "8", "9", "10", "9"}
            };
        } else if (subject.equals("العلوم")) {
            qs = new String[][]{
                    {"ما الغاز الذي نتنفسه؟", "النيتروجين", "الأكسجين", "الهيدروجين", "الهيليوم", "الأكسجين"},
                    {"كم كوكبًا في المجموعة الشمسية؟", "7", "8", "9", "10", "8"},
                    {"ما الحالة التي يتحول فيها الماء لبخار؟", "التجمد", "التبخر", "التكاثف", "الانصهار", "التبخر"},
                    {"العضو المسؤول عن ضخ الدم؟", "الرئة", "الكبد", "القلب", "المعدة", "القلب"}
            };
        } else {
            qs = new String[][]{
                    {"كم حرفًا في اللغة العربية؟", "26", "27", "28", "29", "28"},
                    {"ما نوع كلمة (يكتب)؟", "اسم", "فعل مضارع", "فعل ماضٍ", "حرف", "فعل مضارع"},
                    {"جمع كلمة (كتاب)؟", "كاتبون", "كتبة", "كتب", "كتابات", "كتب"},
                    {"ما ضد كلمة (كبير)؟", "ضخم", "عظيم", "صغير", "طويل", "صغير"}
            };
        }

        final int[] current = {0};
        final int[] score = {0};

        showQuestion(qs, current, score, subject);
    }

    void showQuestion(String[][] qs, int[] current, int[] score, String subject) {
        if (current[0] >= qs.length) {
            int earned = score[0] * 10;
            int prev = prefs.getInt("points", 0);
            int prevTests = prefs.getInt("tests", 0);
            prefs.edit()
                    .putInt("points", prev + earned)
                    .putInt("tests", prevTests + 1)
                    .apply();

            new AlertDialog.Builder(this)
                    .setTitle("🎉 انتهى الاختبار")
                    .setMessage("أجبت صح على " + score[0] + " من " + qs.length + "\n\nالنقاط المكتسبة: +" + earned)
                    .setPositiveButton("رائع!", (d, w) -> showProgress())
                    .setCancelable(false)
                    .show();
            return;
        }

        String[] q = qs[current[0]];
        String[] options = {q[1], q[2], q[3], q[4]};

        new AlertDialog.Builder(this)
                .setTitle(subject + " — سؤال " + (current[0] + 1) + "/" + qs.length)
                .setMessage(q[0])
                .setCancelable(false)
                .setSingleChoiceItems(options, -1, (d, which) -> {
                    if (options[which].equals(q[5])) {
                        score[0]++;
                        Toast.makeText(this, "✅ إجابة صحيحة", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "❌ الصحيح: " + q[5], Toast.LENGTH_SHORT).show();
                    }
                    d.dismiss();
                    current[0]++;
                    showQuestion(qs, current, score, subject);
                })
                .show();
    }

    void showProgress() {
        base("تقدمي");

        int points = prefs.getInt("points", 0);
        int tests = prefs.getInt("tests", 0);
        int lessons = prefs.getInt("lessons", 0);

        actionStat("⭐", "النقاط", String.valueOf(points), ORANGE);
        actionStat("📝", "الاختبارات المكتملة", String.valueOf(tests), BLUE);
        actionStat("📚", "الدروس المكتملة", String.valueOf(lessons), GREEN);

        TextView note = tv("استمر! كل اختبار يمنحك 10 نقاط عن كل إجابة صحيحة.", 13, GRAY, false);
        note.setPadding(0, dp(16), 0, 0);
        content.addView(note);

        nav("progress");
    }

    void actionStat(String emoji, String label, String value, int color) {
        CardView c = card();
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(18), dp(18), dp(18), dp(18));

        TextView ico = tv(emoji, 22, WHITE, false);
        ico.setGravity(Gravity.CENTER);
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(color);
        ico.setBackground(circle);
        row.addView(ico, new LinearLayout.LayoutParams(dp(48), dp(48)));

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);
        texts.setPadding(dp(14), 0, 0, 0);
        texts.addView(tv(label, 14, GRAY, false));

        TextView val = tv(value, 24, DARK, true);
        val.setPadding(0, dp(2), 0, 0);
        texts.addView(val);

        row.addView(texts, new LinearLayout.LayoutParams(0, -2, 1));
        c.addView(row);
        content.addView(c, lpFull(-2));
    }
}
