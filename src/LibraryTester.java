    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.*;
    import java.util.stream.Collectors;

    // todo: implement the necessary classes
    class Book{
        String isbn;
        String title;
        int releaseYear;
        String author;
        int brojPrimeroci;
        int brojPozajmuvanja;

        public Book(String isbn, String title, String author, int releaseYear,int brojPrimeroci) {
            this.isbn = isbn;
            this.title = title;
            this.author=author;
            this.releaseYear = releaseYear;
            this.brojPrimeroci=brojPrimeroci;
            this.brojPozajmuvanja=0;
        }


        public void setBrojPrimeroci(int brojPrimeroci) {
            this.brojPrimeroci = brojPrimeroci;
        }
        public void zgolemiBrojPrimeroci() {
            this.brojPrimeroci ++;
        }
        public void namaliBrojPrimeroci() {
            this.brojPrimeroci --;
        }
        public void zgolemiBrojpozajmuvanja() {
            this.brojPozajmuvanja ++;
        }


        public int getBrojPrimeroci() {
            return brojPrimeroci;
        }

    }
    class Member{
            String id;
            String name;
            int brojPozajmeni;
            int totalBorows;

        public Member(String id, String name, int brojPozajmeni) {
            this.id = id;
            this.name = name;
            this.brojPozajmeni=brojPozajmeni;
            this.totalBorows=0;
        }
        public Member(String id,String name) {
            this.id = id;
        }

        public void setBrojPozajmeni(int brojPozajmeni) {
            this.brojPozajmeni = brojPozajmeni;
        }
        public void namaliPozajmeni (){
            this.brojPozajmeni --;
        }
        public void zgolemiPozajmeni (){
            this.brojPozajmeni ++;
        }
        public void zgolemitotal (){
            this.totalBorows ++;
        }

        public int getBrojPozajmeni() {
            return brojPozajmeni;
        }

        @Override
        public String toString() {
            return this.name+" ("+id+") - borrowed now: "+brojPozajmeni+", total borrows: "+totalBorows;
        }
    }
    class LibrarySystem{
            String libName;
            TreeMap<String, Member> members;
            TreeMap<String,Book> books;
            TreeMap<String,Member> cekanje;

        public LibrarySystem(String libName) {
            this.libName = libName;
            this.members= new TreeMap<>();
            this.books= new TreeMap<>();
            this.cekanje= new TreeMap<>();
        }
        public void registerMember(String id, String fullName){
            members.put(id,new Member(id,fullName,0));
        }
        public void addBook(String isbn, String title, String author, int year){
            if(!books.containsKey(isbn)){
                books.put(isbn,new Book(isbn,title,author,year,1));
            }else{
                books.get(isbn).setBrojPrimeroci(books.get(isbn).getBrojPrimeroci()+1);
            }
        }
        public void borrowBook(String memberId, String isbn){
            if(books.get(isbn)!=null){
                if(books.get(isbn).getBrojPrimeroci()<1){
                    cekanje.put(isbn,members.get(memberId));
                }else{
                    members.get(memberId).zgolemiPozajmeni();
                    members.get(memberId).zgolemitotal();
                    books.get(isbn).zgolemiBrojpozajmuvanja();
                    books.get(isbn).setBrojPrimeroci(books.get(isbn).getBrojPrimeroci()-1);
                }
            }
        }
        public void returnBook(String memberId, String isbn){
            books.get(isbn).zgolemiBrojPrimeroci();
            cekanje.entrySet().stream().findFirst().stream().map(Map.Entry::getValue).forEach(Member::namaliPozajmeni);

            if(cekanje.get(isbn)!=null){
                cekanje.entrySet().stream().findFirst().stream().map(Map.Entry::getValue).forEach(Member::zgolemiPozajmeni);
                cekanje.entrySet().stream().findFirst().stream().map(Map.Entry::getValue).forEach(Member::zgolemitotal);
                books.get(isbn).setBrojPrimeroci(books.get(isbn).getBrojPrimeroci()-1);
                cekanje.remove(cekanje.firstKey());
            }
        }
        public void printMembers(){
            members.entrySet().stream().map(r->r.getValue()).sorted(Comparator.comparingInt(Member::getBrojPozajmeni)).forEach(r-> System.out.println(r.toString()));
        }
        void printBookCurrentBorrowers(String isbn){
            members.entrySet().stream().filter(r->r.getKey().equals(isbn)).map(r->r.getValue()).sorted(Comparator.comparingInt(Member::getBrojPozajmeni)).forEach(r->{
                System.out.println(r.toString()+",");
            });
        }
        void printBooks(){
            System.out.println("da");
        }
         void printTopAuthors(){
             System.out.println("da");
        }

    }



    public class LibraryTester {
        public static void main(String[] args) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                String libraryName = br.readLine();
                //   System.out.println(libraryName); //test
                if (libraryName == null) return;

                libraryName = libraryName.trim();
                LibrarySystem lib = new LibrarySystem(libraryName);

                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.equals("END")) break;
                    if (line.isEmpty()) continue;

                    String[] parts = line.split(" ");

                    switch (parts[0]) {

                        case "registerMember": {
                            lib.registerMember(parts[1], parts[2]);
                            break;
                        }

                        case "addBook": {
                            String isbn = parts[1];
                            String title = parts[2];
                            String author = parts[3];
                            int year = Integer.parseInt(parts[4]);
                            lib.addBook(isbn, title, author, year);
                            break;
                        }

                        case "borrowBook": {
                            lib.borrowBook(parts[1], parts[2]);
                            break;
                        }

                        case "returnBook": {
                            lib.returnBook(parts[1], parts[2]);
                            break;
                        }

                        case "printMembers": {
                            lib.printMembers();
                            break;
                        }

                        case "printBooks": {
                            lib.printBooks();
                            break;
                        }

                        case "printBookCurrentBorrowers": {
                            lib.printBookCurrentBorrowers(parts[1]);
                            break;
                        }

                        case "printTopAuthors": {
                            lib.printTopAuthors();
                            break;
                        }

                        default:
                            break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
