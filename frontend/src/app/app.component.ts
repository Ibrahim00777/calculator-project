import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({ selector: 'app-root', standalone: true, imports: [CommonModule], templateUrl: './app.component.html', styleUrl: './app.component.css' })
export class AppComponent {
  display = '0'; previous = ''; operator = ''; waiting = false; history: { expression: string; result: string }[] = []; error = '';
  constructor(private http: HttpClient) {}
  press(key: string) {
    this.error = '';
    if (/^[0-9.]$/.test(key)) { if (key === '.' && this.display.includes('.')) return; this.display = this.waiting ? (key === '.' ? '0.' : key) : (this.display === '0' && key !== '.' ? key : this.display + key); this.waiting = false; return; }
    if (['+','-','×','÷'].includes(key)) { if (this.operator && !this.waiting) this.calculate(); this.previous = this.display; this.operator = key; this.waiting = true; return; }
    if (key === 'C') { this.display = '0'; this.previous = ''; this.operator = ''; this.waiting = false; return; }
    if (key === '±') { this.display = String(Number(this.display) * -1); return; }
    if (key === '%') { this.display = String(Number(this.display) / 100); return; }
    if (key === '=') this.calculate();
  }
  calculate() {
    if (!this.operator || !this.previous) return;
    const payload = { firstNumber: Number(this.previous), secondNumber: Number(this.display), operation: this.operator };
    this.http.post<{result: number; expression: string}>('/api/calculator/calculate', payload).subscribe({ next: res => { const value = Number.isInteger(res.result) ? String(res.result) : String(Number(res.result.toFixed(10))); this.history.unshift({ expression: res.expression, result: value }); this.display = value; this.previous = ''; this.operator = ''; this.waiting = true; }, error: err => { this.error = err.error || 'Unable to reach the calculator API.'; } });
  }
}
