4.Flux 和 Mono

Flux 和 Mono 是 Reactor 中的两个基本概念。Flux 表示的是包含 0 到 N 个元素的异步序列。在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。Mono 表示的是包含 0 或者 1 个元素的异步序列。该序列中同样可以包含与 Flux 相同的三种类型的消息通知。Flux 和 Mono 之间可以进行转换。对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象。把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。

List <Mono> == Flux

创建 Flux
第一种方式是通过 Flux 类中的静态方法。

just()：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
empty()：创建一个不包含任何元素，只发布结束消息的序列。
error(Throwable error)：创建一个只包含错误消息的序列。
never()：创建一个不包含任何消息通知的序列。
range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
intervalMillis(long period)和 intervalMillis(long delay, long period)：与 interval()方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。
generate()方法


create()方法




创建 Mono

Mono 的创建方式与之前介绍的 Flux 比较相似。Mono 类中也包含了一些与 Flux 类中相同的静态方法。这些方法包括 just()，empty()，error()和 never()等。除了这些方法之外，Mono 还有一些独有的静态方法。

fromCallable()、fromCompletionStage()、fromFuture()、fromRunnable()和 fromSupplier()：分别从 Callable、CompletionStage、CompletableFuture、Runnable 和 Supplier 中创建 Mono。
delay(Duration duration)和 delayMillis(long duration)：创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值。
ignoreElements(Publisher<T> source)：创建一个 Mono 序列，忽略作为源的 Publisher 中的所有元素，只产生结束消息。
justOrEmpty(Optional<? extends T> data)和 justOrEmpty(T data)：从一个 Optional 对象或可能为 null 的对象中创建 Mono。只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。
还可以通过 create()方法来使用 MonoSink 来创建 Mono。代码清单 4 中给出了创建 Mono 序列的示例。


5.操作符

buffer 和 bufferTimeout

这两个操作符的作用是把当前流中的元素收集到集合中，并把集合对象作为流中的新元素。在进行收集时可以指定不同的条件：所包含的元素的最大数量或收集的时间间隔。方法 buffer()仅使用一个条件，而 bufferTimeout()可以同时指定两个条件。指定时间间隔时可以使用 Duration 对象或毫秒数，即使用 bufferMillis()或 bufferTimeoutMillis()两个方法。

除了元素数量和时间间隔之外，还可以通过 bufferUntil 和 bufferWhile 操作符来进行收集。这两个操作符的参数是表示每个集合中的元素所要满足的条件的 Predicate 对象。bufferUntil 会一直收集直到 Predicate 返回为 true。使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中；bufferWhile 则只有当 Predicate 返回 true 时才会收集。一旦值为 false，会立即开始下一次收集。


代码清单 5 给出了 buffer 相关操作符的使用示例。第一行语句输出的是 5 个包含 20 个元素的数组；第二行语句输出的是 2 个包含了 10 个元素的数组；第三行语句输出的是 5 个包含 2 个元素的数组。每当遇到一个偶数就会结束当前的收集；第四行语句输出的是 5 个包含 1 个元素的数组，数组里面包含的只有偶数。

需要注意的是，在代码清单 5 中，首先通过 toStream()方法把 Flux 序列转换成 Java 8 中的 Stream 对象，再通过 forEach()方法来进行输出。这是因为序列的生成是异步的，而转换成 Stream 对象可以保证主线程在序列生成完成之前不会退出，从而可以正确地输出序列中的所有元素。

6.filter

对流中包含的元素进行过滤，只留下满足 Predicate 指定条件的元素。代码清单 6 中的语句输出的是 1 到 10 中的所有偶数。

Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

7.zipWith

zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。在合并时可以不做任何处理，由此得到的是一个元素类型为 Tuple2 的流；也可以通过一个 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值。


两个流中包含的元素分别是 a，b 和 c，d。第一个 zipWith 操作符没有使用合并函数，因此结果流中的元素类型为 Tuple2；第二个 zipWith 操作通过合并函数把元素类型变为 String。

8.take

take 系列操作符用来从当前流中提取元素。提取的方式可以有很多种。

take(long n)，take(Duration timespan)和 takeMillis(long timespan)：按照指定的数量或时间间隔来提取。
takeLast(long n)：提取流中的最后 N 个元素。
takeUntil(Predicate<? super T> predicate)：提取元素直到 Predicate 返回 true。
takeWhile(Predicate<? super T> continuePredicate)： 当 Predicate 返回 true 时才进行提取。
takeUntilOther(Publisher<?> other)：提取元素直到另外一个流开始产生元素。

第一行语句输出的是数字 1 到 10；第二行语句输出的是数字 991 到 1000；第三行语句输出的是数字 1 到 9；第四行语句输出的是数字 1 到 10，使得 Predicate 返回 true 的元素也是包含在内的。

9.reduce 和 reduceWith

reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。累积操作是通过一个 BiFunction 来表示的。在操作时可以指定一个初始值。如果没有初始值，则序列的第一个元素作为初始值。


第一行语句对流中的元素进行相加操作，结果为 5050；第二行语句同样也是进行相加操作，不过通过一个 Supplier 给出了初始值为 100，所以结果为 5150。

二、消息处理

当需要处理 Flux 或 Mono 中的消息时，如之前的代码清单所示，可以通过 subscribe 方法来添加相应的订阅逻辑。在调用 subscribe 方法时可以指定需要处理的消息类型。可以只处理其中包含的正常消息，也可以同时处理错误消息和完成消息。



当出现错误时，有多种不同的处理策略。第一种策略是通过 onErrorReturn()方法返回一个默认值。
三、调度器

前面介绍了反应式流和在其上可以进行的各种操作，通过调度器（Scheduler）可以指定这些操作执行的方式和所在的线程。有下面几种不同的调度器实现。

当前线程，通过 Schedulers.immediate()方法来创建。
单一的可复用的线程，通过 Schedulers.single()方法来创建。
使用弹性的线程池，通过 Schedulers.elastic()方法来创建。线程池中的线程是可以复用的。当所需要时，新的线程会被创建。如果一个线程闲置太长时间，则会被销毁。该调度器适用于 I/O 操作相关的流的处理。
使用对并行操作优化的线程池，通过 Schedulers.parallel()方法来创建。其中的线程数量取决于 CPU 的核的数量。该调度器适用于计算密集型的流的处理。
使用支持任务调度的调度器，通过 Schedulers.timer()方法来创建。
从已有的 ExecutorService 对象中创建调度器，通过 Schedulers.fromExecutorService()方法来创建。
某些操作符默认就已经使用了特定类型的调度器。比如 intervalMillis()方法创建的流就使用了由 Schedulers.timer()创建的调度器。通过 publishOn()和 subscribeOn()方法可以切换执行操作的调度器。其中 publishOn()方法切换的是操作符的执行方式，而 subscribeOn()方法切换的是产生流中元素时的执行方式。


使用 create()方法创建一个新的 Flux 对象，其中包含唯一的元素是当前线程的名称。接着是两对 publishOn()和 map()方法，其作用是先切换执行时的调度器，再把当前的线程名称作为前缀添加。最后通过 subscribeOn()方法来改变流产生时的执行方式。运行之后的结果是[elastic-2] [single-1] parallel-1。最内层的线程名字 parallel-1 来自产生流中元素时使用的 Schedulers.parallel()调度器，中间的线程名称 single-1 来自第一个 map 操作之前的 Schedulers.single()调度器，最外层的线程名字 elastic-2 来自第二个 map 操作之前的 Schedulers.elastic()调度器。