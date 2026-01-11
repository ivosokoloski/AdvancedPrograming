import java.util.ArrayList;
import java.util.Scanner;

class TriviaQuestion {

    public static final int TRUEFALSE = 0;
    public static final int FREEFORM = 1;
    private String question;
    private String answer;
    private int value;
    private int type;

    public TriviaQuestion(String question, String answer, int value, int type) {
        this.question = question;
        this.answer = answer;
        this.value = value;
        this.type = type;
    }

    public void showQuestion(int index) {
        System.out.println("Question " + (index + 1) + ".  " + value + " points.");
        System.out.println(question);
        if (type == TRUEFALSE) {
            System.out.println("Enter 'T' for true or 'F' for false.");
        }
    }

    public boolean checkAnswer(String userAnswer) {
        if (type == TRUEFALSE) {
            return userAnswer.length() > 0 && userAnswer.charAt(0) == answer.charAt(0);
        } else {
            return userAnswer.equalsIgnoreCase(answer);
        }
    }

    public int getValue() {
        return value;
    }

    public String getCorrectAnswer() {
        return answer;
    }
}

class TriviaData {
    private ArrayList<TriviaQuestion> questions;

    public TriviaData() {
        questions = new ArrayList<>();
    }

    public void addQuestion(TriviaQuestion question) {
        questions.add(question);
    }

    public TriviaQuestion getQuestion(int index) {
        return questions.get(index);
    }

    public int numQuestions() {
        return questions.size();
    }
}

public class TriviaGame {

    private TriviaData questions;

    public TriviaGame() {
        questions = new TriviaData();
        questions.addQuestion(new TriviaQuestion(
                "The possession of more than two sets of chromosomes is termed?",
                "polyploidy", 3, TriviaQuestion.FREEFORM));
        questions.addQuestion(new TriviaQuestion(
                "Erling Kagge skiied into the north pole alone on January 7, 1993.",
                "F", 1, TriviaQuestion.TRUEFALSE));
        questions.addQuestion(new TriviaQuestion(
                "1997 British band that produced 'Tub Thumper'",
                "Chumbawumba", 2, TriviaQuestion.FREEFORM));
        questions.addQuestion(new TriviaQuestion(
                "I am the geometric figure most like a lost parrot",
                "polygon", 2, TriviaQuestion.FREEFORM));
        questions.addQuestion(new TriviaQuestion(
                "Generics were introducted to Java starting at version 5.0.",
                "T", 1, TriviaQuestion.TRUEFALSE));
    }

    public void play() {
        int score = 0;
        Scanner keyboard = new Scanner(System.in);

        for (int i = 0; i < questions.numQuestions(); i++) {
            TriviaQuestion q = questions.getQuestion(i);
            q.showQuestion(i);

            String userAnswer = keyboard.nextLine();

            if (q.checkAnswer(userAnswer)) {
                System.out.println("That is correct!  You get " + q.getValue() + " points.");
                score += q.getValue();
            } else {
                System.out.println("Wrong, the correct answer is " + q.getCorrectAnswer());
            }

            System.out.println("Your score is " + score);
        }

        System.out.println("Game over!  Thanks for playing!");
    }

    public static void main(String[] args) {
        TriviaGame game = new TriviaGame();
        game.play();
    }
}
