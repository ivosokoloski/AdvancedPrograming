    import java.util.*;
    import java.util.stream.Collectors;
    import java.util.stream.IntStream;

    interface XMLComponent{
        void addAttribute(String attr, String value);
        void display(int level);
    }

    class XMLLeaf implements XMLComponent{
        String mainAttr;
        String mainValue;

        Map<String,String> atrVal;

        public XMLLeaf(String attr, String value) {
            this.mainAttr = attr;
            this.mainValue = value;
            atrVal= new LinkedHashMap<>();
        }

        @Override
        public void addAttribute(String attr, String value) {
            atrVal.putIfAbsent(attr,value);
        }

        @Override
        public void display(int level) {
            System.out.print("    ".repeat(level));
            System.out.print("<"+mainAttr);
            for (Map.Entry<String, String> stringStringEntry : atrVal.entrySet()) {
                System.out.print(" "+stringStringEntry.getKey()+"=\""+stringStringEntry.getValue()+"\"");
            }
            System.out.print(">"+mainValue);
            System.out.print("</"+mainAttr+">\n");

        }
    }
    class XMLComposite implements XMLComponent{

        String Name;
        Map<String,String> atrVal;
        List<XMLComponent> components;

        public XMLComposite(String name) {
            Name = name;
            atrVal= new LinkedHashMap<>();
            components= new ArrayList<>();

        }

        @Override
        public void addAttribute(String attr, String value) {
            atrVal.putIfAbsent(attr,value);
        }
        public void addComponent(XMLComponent component) {
            components.add(component);
        }


        @Override
        public void display(int level) {
            System.out.print("    ".repeat(level));
            System.out.print("<"+Name);
            for (Map.Entry<String, String> stringStringEntry : atrVal.entrySet()) {
                System.out.print(" "+stringStringEntry.getKey()+"=\""+stringStringEntry.getValue()+"\"");
            }
            System.out.print(">\n");
            for (XMLComponent component : components) {
               component.display(level+1);
            }
            System.out.print("    ".repeat(level));
            System.out.print("</"+Name+">\n");

        }
    }


    public class XMLTest {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int testCase = sc.nextInt();
            XMLComponent component = new XMLLeaf("student", "Trajce Trajkovski");
            component.addAttribute("type", "redoven");
            component.addAttribute("program", "KNI");

            XMLComposite composite = new XMLComposite("name");
            composite.addComponent(new XMLLeaf("first-name", "trajce"));
            composite.addComponent(new XMLLeaf("last-name", "trajkovski"));
            composite.addAttribute("type", "redoven");
            component.addAttribute("program", "KNI");

            if (testCase==1) {
                //TODO Print the component object
                component.display(0);
            } else if(testCase==2) {
                //TODO print the composite object
                composite.display(0);
            } else if (testCase==3) {
                XMLComposite main = new XMLComposite("level1");
                main.addAttribute("level","1");
                XMLComposite lvl2 = new XMLComposite("level2");
                lvl2.addAttribute("level","2");
                XMLComposite lvl3 = new XMLComposite("level3");
                lvl3.addAttribute("level","3");
                lvl3.addComponent(component);
                lvl2.addComponent(lvl3);
                lvl2.addComponent(composite);
                lvl2.addComponent(new XMLLeaf("something", "blabla"));
                main.addComponent(lvl2);
                main.addComponent(new XMLLeaf("course", "napredno programiranje"));

                //TODO print the main object
                main.display(0);
            }
        }
    }
