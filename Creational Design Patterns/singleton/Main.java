//this is singleton creational pattern .

//------------------------------------------------------------
//eager loading which is thread safe
// class CodeJudge {
//     public static final CodeJudge judge = new CodeJudge();

//     private CodeJudge() {

//     }

//     public static CodeJudge getInstace() {
//         return judge;
//     }
// }
//---------------------------------------------------
//lazy loading which is not thread safe
// class CodeJudge {
//     public static CodeJudge judge;

//     private CodeJudge() {

//     }

//     public static CodeJudge getInstace() {
//         if (judge == null)
//             judge = new CodeJudge();
//         return judge;
//     }
// }
//---------------------------------------------------------------
//lazy loading which is thread safe using synchronized
// class CodeJudge {
//     public static CodeJudge judge;

//     private CodeJudge() {

//     }

//     public static synchronized CodeJudge getInstace() {
//         if (judge == null)
//             judge = new CodeJudge();
//         return judge;
//     }
// }
//----------------------------------------------------------------------
//lazy loading which is thread safe using double check synchronized
class CodeJudge {
    public static volatile CodeJudge judge;

    private CodeJudge() {

    }

    public static CodeJudge getInstace() {
        if (judge == null)
            synchronized (CodeJudge.class) {
                if (judge == null)
                    judge = new CodeJudge();
            }

        return judge;
    }
}

public class Main {
    public static void main(String[] args) {
        CodeJudge judge1 = CodeJudge.getInstace();
        CodeJudge judge2 = CodeJudge.getInstace();

        System.out.println(judge1);
        System.out.println(judge2);
    }
}
