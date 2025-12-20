
    import java.lang.reflect.InvocationTargetException;
    import java.lang.reflect.Method;
    import java.util.*;


    class NoSuchRoomException extends Throwable {
        public NoSuchRoomException(String roomName) {
            System.out.println(roomName);
        }
    }
    class NoSuchUserException extends Throwable {
        public NoSuchUserException(String roomName) {
            System.out.println(roomName);
        }
    }
    class ChatRoom{
        private String nameOfRoom;
        private List<String> users;

        public ChatRoom(String nameOfRoom) {
            this.nameOfRoom = nameOfRoom;
            this.users = new ArrayList<>();
        }
        public void addUser(String username){
            this.users.add(username);
        }
        public void removeUser(String username){
           this.users.removeIf(user->user.equals(username));
        }
        public boolean hasUser(String username){
            return users.contains(username);
        }

        @Override
        public String toString() {
            StringBuilder sb= new StringBuilder();
            sb.append(nameOfRoom).append("\n");
            if(users.isEmpty()){
                sb.append("EMPTY");
                return sb.toString()    ;
            }
            this.users.stream().sorted().forEach(u->sb.append(u).append("\n"));
            return sb.toString();
        }
        public int numUsers(){
            return this.users.size();
        }

        public String getNameOfRoom() {
            return nameOfRoom;
        }

        public List<String> getUsers() {
            return users;
        }
    }
    class ChatSystem{
        TreeMap<String,ChatRoom> rooms ;
        private List<String> users;

        public ChatSystem() {
            this.rooms= new TreeMap<>();
            this.users = new ArrayList<>();
        }
        public void addRoom(String roomName){
            this.rooms.put(roomName,new ChatRoom(roomName));

        }
        public void removeRoom(String roomName){
            rooms.remove(roomName);
        }
        public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
            if(rooms.get(roomName)==null){
                throw new NoSuchRoomException(roomName);

            }
            return rooms.get(roomName);
        }

        public void register(String userName) throws NoSuchRoomException {
            users.add(userName);
             rooms.entrySet()
                    .stream()
                    .min(Comparator.comparingInt(r -> r.getValue().getUsers().size())).ifPresent(roomEntry -> roomEntry.getValue().addUser(userName));

        }
        public void registerAndJoin(String userName, String roomName) throws NoSuchRoomException {
            users.add(userName);
            Optional <Map.Entry<String, ChatRoom> >res= rooms.entrySet().stream().filter(r->r.getKey().equals(roomName)).findFirst();
            if (res.isPresent()) {
                res.get().getValue().addUser(userName);
            } else {
                throw new NoSuchRoomException("No rooms available");
            }
        }
        public void joinRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
            if(!users.contains(userName)){
                throw new NoSuchUserException(userName);
            }
            if(rooms.get(roomName)==null){
                throw new NoSuchRoomException(roomName);
            }
            Optional<Map.Entry<String, ChatRoom>> res= rooms.entrySet().stream().filter(r->r.getKey().equals(roomName)).findFirst();
            if (res.isPresent()) {
                res.get().getValue().addUser(userName);
            } else {
                throw new NoSuchRoomException("No rooms available");
            }

        }
        public void leaveRoom(String username, String roomName) throws NoSuchUserException, NoSuchRoomException {
            if(!users.contains(username)){
                throw new NoSuchUserException(username);
            }
            if(rooms.get(roomName)==null){
                throw new NoSuchRoomException(roomName);
            }
            Optional<Map.Entry<String, ChatRoom>> res= rooms.entrySet().stream().filter(r->r.getKey().equals(roomName)).findFirst();
            if (res.isPresent()) {
                res.get().getValue().removeUser(username);
            } else {
                throw new NoSuchRoomException("No rooms available");
            }
        }
        public void followFriend(String username, String friend_username) throws NoSuchUserException {
            if(!users.contains(username)){
                throw new NoSuchUserException(username);
            }

            rooms.entrySet().stream().filter(r->r.getValue().getUsers().contains(friend_username)).forEach(r->{
                if(!r.getValue().hasUser(username)){
                    r.getValue().addUser(username);
                }

            });
        }

    }


    public class ChatSystemTest {

        public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
            Scanner jin = new Scanner(System.in);
            int k = jin.nextInt();
            if ( k == 0 ) {
                ChatRoom cr = new ChatRoom(jin.next());
                int n = jin.nextInt();
                for ( int i = 0 ; i < n ; ++i ) {
                    k = jin.nextInt();
                    if ( k == 0 ) cr.addUser(jin.next());
                    if ( k == 1 ) cr.removeUser(jin.next());
                    if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
                }
                System.out.println("");
                System.out.println(cr.toString());
                n = jin.nextInt();
                if ( n == 0 ) return;
                ChatRoom cr2 = new ChatRoom(jin.next());
                for ( int i = 0 ; i < n ; ++i ) {
                    k = jin.nextInt();
                    if ( k == 0 ) cr2.addUser(jin.next());
                    if ( k == 1 ) cr2.removeUser(jin.next());
                    if ( k == 2 ) cr2.hasUser(jin.next());
                }
                System.out.println(cr2.toString());
            }
            if ( k == 1 ) {
                ChatSystem cs = new ChatSystem();
                Method mts[] = cs.getClass().getMethods();
                while ( true ) {
                    String cmd = jin.next();
                    if ( cmd.equals("stop") ) break;
                    if ( cmd.equals("print") ) {
                        System.out.println(cs.getRoom(jin.next())+"\n");continue;
                    }
                    for ( Method m : mts ) {
                        if ( m.getName().equals(cmd) ) {
                            String params[] = new String[m.getParameterTypes().length];
                            for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                            m.invoke(cs,(Object[])params);
                        }
                    }
                }
            }
        }

    }
