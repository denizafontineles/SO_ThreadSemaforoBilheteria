package controller;

import java.util.concurrent.Semaphore;

public class ThreadSemaforoBilheteria extends Thread {

	private Semaphore semaforo;
	private int idUsuario;
	private int qtdTotalIngressos = 100;
	private int esperaLogin;
	private int esperaCompra;
	private int qtdCompraIngresso;
	
	public ThreadSemaforoBilheteria(int idUsuario, Semaphore semaforo) {
		this.idUsuario = idUsuario;
		this.semaforo = semaforo;
	}
	
	public void run() {
		if(loginSistema()) {
			if(processoCompra()) {
				try {
					semaforo.acquire();
					validaCompra();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					semaforo.release();
				}
			}
		}
	}
	
	private boolean loginSistema() {
		this.esperaLogin = random(500, 2001);
		
		try {
			sleep(this.esperaLogin);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(this.esperaLogin >= 1000) {
			System.out.println("Usuário #" + idUsuario + ": ERRO DE TIMEOUT ao logar do sistema em " + this.esperaLogin + "ms.");
			return false;
		} else {
			System.out.println("Usuário #" + idUsuario + ": SUCESSO ao logar no sistema após " + this.esperaLogin + "ms.");
			this.qtdCompraIngresso = random(1, 5);
			return true;
		}
	}
	
	private boolean processoCompra() {
		this.esperaCompra = random(1000, 3001);
		try {
			sleep(this.esperaCompra);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(this.esperaCompra >= 2500) {
			System.out.println("Usuário #" + idUsuario + ": SESSÃO FINALIZADA! - Tempo esperado " + this.esperaCompra + "ms.");
			return false;
		}
		
		return true;
	}
	
	private boolean validaCompra() {
		if(qtdTotalIngressos >= this.qtdCompraIngresso) {
			qtdTotalIngressos -= this.qtdCompraIngresso;
			System.out.println("Usuário #" + idUsuario + " PARABÉNS você comprou " + this.qtdCompraIngresso + " ingressos e restam: " + qtdTotalIngressos + " ingressos.");
			return true;
		} else {
			System.out.println("Usuário #" + idUsuario + " ERRO ao comprar " + this.qtdCompraIngresso + "ingressos pois restam apenas: " + qtdTotalIngressos + " ingressos.");
			return false;
		}
	}
	
	public int random(int min, int max){
		int numeroAleatorio = min + (int)(Math.random() * (max - min));
	    return numeroAleatorio;
	}
}
