import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { ConfigurationFormComponent } from './components/configuration-form/configuration-form.component'; // Check this path

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationFormComponent, // Ensure this is declared
  ],
  imports: [
    BrowserModule,
    FormsModule, // Required for [(ngModel)]
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
