﻿namespace FPFriender
{
    partial class DoTyper
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.tmrSendKeys2 = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // tmrSendKeys2
            // 
            this.tmrSendKeys2.Tick += new System.EventHandler(this.tmrSendKeys2_Tick);
            // 
            // DoTyper
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(359, 89);
            this.Name = "DoTyper";
            this.Text = "DoTyper";
            this.ResumeLayout(false);

        }

        #endregion

        public System.Windows.Forms.Timer tmrSendKeys2;

    }
}