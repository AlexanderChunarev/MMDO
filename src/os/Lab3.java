package os;

public class Lab3 {
    public static void main(String [] args) {
        // Показати заголовки стовпцiв виведення
        StringBuffer columnHeads = new StringBuffer("Дiя\t\t\t\t");
        columnHeads.append("Буфер\tKiлькiсть зайнятих елементiв\n");
        System.err.println(columnHeads);
        // Створити кiльцевий буфер, що використовується потоками
        SynchronizedBuffer sharedLocation = new SynchronizedBuffer(4); //розмiр 3 елементи
        sharedLocation.displayState("Початковий стан\t\t\t"); // усi елементи порожнi
        // створити об'єкти-Bиробники i об'єкти-Cпоживачi
        Producer Producer_1 = new Producer(sharedLocation, 4); // записує 4 елементи
        Producer_1.setName("Виробник_1");
        Producer Producer_2 = new Producer(sharedLocation, 4);// записує 4 елементи
        Producer_2.setName("Виробник_2");
        Producer Producer_3 = new Producer(sharedLocation, 4);// записує 4 елементи
        Producer_3.setName("Виробник_3");
        Producer Producer_4 = new Producer(sharedLocation, 4);// записує 4 елементи
        Producer_4.setName("Виробник_3");

        Consumer Consumer_1 = new Consumer(sharedLocation, 6);// читає 6 елементiв
        Consumer_1.setName("Споживач_1");
        Consumer Consumer_2 = new Consumer(sharedLocation, 6);// читає 6 елементiв
        Consumer_2.setName("Споживач_2");
        // запуск потокiв
        //1,2,4; 2,3; 1,3; 3,4;
        Producer_1.start();
        Producer_2.start();
        Producer_4.start();

        Consumer_1.start();
        Consumer_2.start();

        Producer_3.start();
        Producer_2.start();

        Consumer_1.start();
        Consumer_2.start();

        Producer_1.start();
        Producer_3.start();

        Consumer_1.start();
        Consumer_2.start();

        Producer_3.start();
        Producer_4.start();

        Consumer_1.start();
        Consumer_2.start();
    }
}
// Kiльцевий буфер
class SynchronizedBuffer {
    private int max_size; //максимальний розмiр
    private int[] buffer; // масив елементiв
    private int buf_elcount; //поточний розмiр
    private int get_pos; //позицiя для читання
    private int set_pos; //позицiя для запису
    // конструктор
    public SynchronizedBuffer(int asize) {
        max_size = asize;
        buffer = new int[max_size];
        buf_elcount = 0;
        get_pos = 0;
        set_pos = 0;
    }
    // записати елемент
    public synchronized void set(int value) {
        // Iм'я поточного потоку
        String name = Thread.currentThread().getName();
        // якщо буфер повнiстю заповнений - чекати i вивести повiдомлення
        while (buf_elcount == max_size) {
            try {
                System.out.println(name+" робить спробу писати.");
                displayState("Буфер повний. "+name+" чекає.\t");
                wait();
            }
            // якщо чекаючий поток перервано, вивести дерево викликiв
            catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        // якщо є вiльнi мiсця - записати елемент
        buffer[set_pos] = value;
        buf_elcount++;
        displayState(name+" writes "+Integer.toString(buffer[set_pos])+"\t\t");
        if (set_pos == max_size-1)
            set_pos = 0;
        else
            set_pos++;
        // повiдомити усi чекаючi потоки про можливiсть продовжити роботу
        if (buf_elcount == 1)
            notifyAll();
    }
    // зчитати елемент
    public synchronized int get() {
        // iм'я поточного потоку
        String name = Thread.currentThread().getName();
        // якщо буфер порожнiй - чекати i вивести повiдомлення
        while(buf_elcount == 0) {
            try {
                System.out.println(name+" робить спробу читати." );
                displayState("Буфер порожнiй. "+name+" чекає.\t");
                wait();
            }
            // якщо чекаючий поток перервано, вивести дерево викликiв
            catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        // якщо буфер не порожнiй - зчитати елемент
        int temp = buffer[get_pos];
        buffer[get_pos] = 0; //тепер елемент порожнiй
        buf_elcount--;
        displayState(name+" зчитує "+Integer.toString(temp)+"\t\t");
        if (get_pos == max_size-1)
            get_pos = 0;
        else
            get_pos++;
        // повiдомити усi чекаючi потоки про можливiсть продовжити роботу
        if (buf_elcount == max_size-1)
            notifyAll();
        return temp;
    }
    // вивести поточнi елементи i їх кiлькiсть
    public void displayState(String operation) {
        StringBuffer outputLine = new StringBuffer(operation);
        for (int j = 0; j < max_size; j++)
            outputLine.append(Integer.toString(this.buffer[j]) + " ");
        outputLine.append("\t"+buf_elcount+"\n");
        System.out.println(outputLine);
    }
}
// Cпоживач
class Consumer extends Thread {
    private SynchronizedBuffer sharedLocation; // посилання на кiльцевий буфер
    private int el_toread; // кiлькiсть елементiв, якi треба зчитати
    // конструктор
    public Consumer(SynchronizedBuffer shared, int ael_toread) {
        super("Consumer");
        sharedLocation = shared;
        el_toread = ael_toread;
    }
    // зчитати el_toread елементiв i порахувати їх суму
    public void run() {
        int sum = 0;
        for (int count = 1; count <= el_toread; count++) {
            try {
                Thread.sleep((int)(Math.random()*3001));
                sum += sharedLocation.get();
            }
            // якщо сплячий поток перервано, вивести дерево викликiв
            catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        System.err.println(getName()+": "+el_toread+" значень зчитано. Сума: "+sum+".\nзавершення "+getName()+".\n");
    }
}
// Bиробник
class Producer extends Thread {
    private SynchronizedBuffer sharedLocation; // посилання на кiльцевий буфер
    private int el_towrite; // кiлькiсть елементiв, якi треба записати
    // конструктор
    public Producer(SynchronizedBuffer shared, int ael_towrite) {
        super("Producer");
        sharedLocation = shared;
        el_towrite = ael_towrite;
    }
    // записати el_towrite елементiв у буфер
    public void run() {
        for (int count = 1; count <= el_towrite; count++) {
            try {
                Thread.sleep((int)(Math.random()*3001));
                sharedLocation.set(count);
            }
            // якщо сплячий поток перервано, вивести дерево викликiв
            catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        System.err.println(getName()+" закiнчив виробництво. Значень вироблено: "+el_towrite+"\nЗавершення "+getName()+".\n");
    }
}
