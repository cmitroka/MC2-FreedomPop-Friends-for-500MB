<%@ Page Title="Feedback" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Feedback.aspx.cs" Inherits="Feedback" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" runat="server">
    <title>Feedback</title>    
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
<center>
    <h2>Leave Feedback</h2>
    <hr />
        We need and want your feedback!&nbsp;Got 
    an idea that could make this app better - let us know!</p>
    <table style="text-align: left">
        <tr>
            <td>Name:</td>
            <td><asp:TextBox ID="txtName" runat="server" Width="200px"></asp:TextBox></td>
        </tr>
        <tr>
            <td>
                Email:</td>
            <td>
                <asp:TextBox ID="txtEmail" runat="server" Width="200px"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>
                Comment:</td>
            <td>
                <asp:TextBox ID="txtComment" runat="server" Height="79px" TextMode="MultiLine" Width="200px"></asp:TextBox>
            </td>
        </tr>
    </table>
    &nbsp;<p>
        <asp:Button ID="cmdSend" runat="server" onclick="cmdSend_Click" Text="Send" 
            Width="117px" />
        <asp:Button ID="cmdCancel" runat="server" Text="Cancel" Width="124px" 
            onclick="cmdCancel_Click" />
</p>
    </center>
</asp:Content>
