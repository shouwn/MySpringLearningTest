package test.java;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class CompletableFutureTest {

	@Test
	public void thenApply() throws InterruptedException, ExecutionException{
		System.out.println("\n\nthenApply\n\n");
		
		Supplier<String> A = () -> {
			try {
				System.out.println("A 스레드 작업 시작");
				Thread.sleep(2000);
				System.out.println("A 스레드 작업 완료");
				return "A 실행";
			} catch (InterruptedException e){
				e.printStackTrace();
				return "실패";
			}
		};

		Function<String, String> B = (aResult) -> {
			try {
				System.out.println("B 스레드 작업 시작");
				Thread.sleep(1000);
				System.out.println("B 스레드 작업 완료");
				return aResult + " B 실행";
			} catch (InterruptedException e){
				e.printStackTrace();
				return "실패";
			}
		};

		CompletableFuture<String> result = CompletableFuture.
				supplyAsync(A).
				thenApply(aResult -> aResult + " A 성공 -> ");
		
		
		System.out.println("compose before");
		
		result.thenCompose(aSucceedResult -> CompletableFuture.supplyAsync(() -> B.apply(aSucceedResult)));

		System.out.println(result.get());
		
		System.out.println("get after");
		
	}

	@Test
	public void thenCombine() throws InterruptedException, ExecutionException {
		
		System.out.println("\n\nthenCombine\n\n");
		
		Supplier<String> A = () -> {
			try {
				System.out.println(this.startString("A"));
				Thread.sleep(2000);
				System.out.println(this.endString("A"));
				return "A 실행";
			} catch (InterruptedException e){
				e.printStackTrace();
				return "실패";
			}
		};

		Supplier<String> C = () -> {
			try {
				System.out.println(this.startString("C"));
				Thread.sleep(500);
				System.out.println(this.endString("C"));
				return "C 실행";
			} catch (InterruptedException e){
				e.printStackTrace();
				return "실패";
			}
		};

		CompletableFuture<String> result = CompletableFuture.
				supplyAsync(A).
				thenApply(aResult -> aResult + " A 성공 -> ");
				//thenCombine(CompletableFuture.supplyAsync(C), (a, c) -> a + c);

		System.out.println("get before");
		
		result.thenCombine(CompletableFuture.supplyAsync(C), (a, c) -> a + c);
		
		System.out.println(result.get());

		System.out.println("get after");
	}
	
	private String startString(String name) {
		return Thread.currentThread().getName() + " : " + name + " 작업 시작";
	}
	
	private String endString(String name) {
		return Thread.currentThread().getName() + " : " + name + " 작업 완료";
	}
	
	@Test
	public void allOf() throws InterruptedException, ExecutionException {
		
		System.out.println("\n\nallOf\n\n");
		
		Supplier<String> A = () -> {
			try {
				System.out.println(this.startString("A"));
				Thread.sleep(2000);
				System.out.println(this.endString("A"));
				return "A 실행";
			} catch (InterruptedException e){
				e.printStackTrace();
				return "실패";
			}
		};

		Supplier<String> C = () -> {
			try {
				System.out.println(this.startString("C"));
				Thread.sleep(500);
				System.out.println(this.endString("C"));
				return "C 실행";
			} catch (InterruptedException e){
				e.printStackTrace();
				return "실패";
			}
		};

		CompletableFuture<String> a = CompletableFuture.supplyAsync(A);
		CompletableFuture<String> c = CompletableFuture.supplyAsync(C);
		
		CompletableFuture<String> result = CompletableFuture.allOf(a, c)
				.thenApply(ignoredVoid -> a.join() + c.join());
		
		System.out.println("get before");
		
		System.out.println(result.get());

		System.out.println("get after");
	}
}
