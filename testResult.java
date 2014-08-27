
public class testResult {
/* test result
Server: +OK POP3 Server Ready
Client: user alex
Server: +OK Hello alex
Client: pass zz
Server: -ERR Invalid password
Client: pass hello123
Server: +OK alex's maildrop is ready with 3 messages.
Client: list
Server: +OK 3 messages (173293 octets)
Server: 1 1301
Server: 2 2722
Server: 3 169270
Client: list 1
Server: +OK 1 1301 octets
Client: list 2
Server: +OK 2 2722 octets
Client: list 3   
Server: +OK 3 169270 octets
Client: dele 1
Server: +OK message 1 deleted
Client: list
Server: +OK 2 messages (171992 octets)
Server: 2 2722
Server: 3 169270
Client: rset
Server: +OK maildrop has 3 message (173293 octets)
Client: retr 1
Server: +OK 1301 octet 
Server:  Received: from pat.cs.nott.ac.uk by robin.Cs.Nott.AC.UK id ac08796;
Server:           21 Sep 2006 19:00 BST
Server: To: ajp06u@Cs.Nott.AC.UK
Server: Subject: Welcome to CSiT at Nottingham
Server: From: Nick Reynolds <nir@Cs.Nott.AC.UK>
Server: Date: Thu, 21 Sep 2006 18:59:43 BST
Server: Sender: root@Cs.Nott.AC.UK
Server: Message-ID:  <200609211859.aa28057@pat.Cs.Nott.AC.UK>
Server: 
Server: Dear ajp06u,
Server: 
Server: As you are reading this email you will have collected your login details
Server: for the school's machines and successfully logged in. Contained in this
Server: mail are pointers to a few WWW addresses which it is suggested you take
Server: a look at together with a little information about the Technical Services
Server: Group (TSG).
Server: 
Server: 
Server: Technical Services Group
Server: ========================
Server: 
Server: TSG provide support for the School's technical equipment.
Server: 
Server: Please take a look at the TSG Web Pages which contain useful information
Server: on using the machines in the school.
Server: 
Server:    http://www.cs.nott.ac.uk/TSG/
Server: 
Server: A good place to start is the "Overview of Services" page:
Server: 
Server:    http://www.cs.nott.ac.uk/TSG/overview/
Server: 
Server: If you have any technical questions or require technical assistance,
Server: please contact the user support office:
Server: 
Server:    http://www.cs.nott.ac.uk/TSG/general_info/user_support.html
Server: 
Server: Or use the CTS tracking system at:
Server: 
Server:    http://www.cs.nott.ac.uk/TSG/CTS/
Server: 
Server: 
Server: Regards,
Server: Nick Reynolds
Server: (Unix Systems Administrator)
Server: 
Server: 
Client: retr 4
Server: -ERR no such message
Client: top 1 1
Server: +OKReceived: from pat.cs.nott.ac.uk by robin.Cs.Nott.AC.UK id ac08796;
Server:           21 Sep 2006 19:00 BST
Server: To: ajp06u@Cs.Nott.AC.UK
Server: Subject: Welcome to CSiT at Nottingham
Server: From: Nick Reynolds <nir@Cs.Nott.AC.UK>
Server: Date: Thu, 21 Sep 2006 18:59:43 BST
Server: Sender: root@Cs.Nott.AC.UK
Server: Message-ID:  <200609211859.aa28057@pat.Cs.Nott.AC.UK>
Server: Dear ajp06u,
Client: top 1 2
Server: +OKReceived: from pat.cs.nott.ac.uk by robin.Cs.Nott.AC.UK id ac08796;
Server:           21 Sep 2006 19:00 BST
Server: To: ajp06u@Cs.Nott.AC.UK
Server: Subject: Welcome to CSiT at Nottingham
Server: From: Nick Reynolds <nir@Cs.Nott.AC.UK>
Server: Date: Thu, 21 Sep 2006 18:59:43 BST
Server: Sender: root@Cs.Nott.AC.UK
Server: Message-ID:  <200609211859.aa28057@pat.Cs.Nott.AC.UK>
Server: Dear ajp06u,
Server: 
Client: uidl
Server: +OK 
Server: 1 Nj!oogfjA?`f!fhsjXop4J&yby?XW,L0xM:R>y"]sR@b<Pz+SC
Server: 2 D*PfZ`v43_%4NiI1v{_`SA!~tM5yK{h(D|LID+V&%d\QoLWU2.
Server: 3 rUi_^I8(%kQuYxZW*4f;7egWZLfJUn(R@Rdyg]p}UtP+Tg@nAj
Client: uidl 1  
Server: +OK 
Server: Nj!oogfjA?`f!fhsjXop4J&yby?XW,L0xM:R>y"]sR@b<Pz+SC
Client: stat
Server: +OK 3 173293
Client: noop
Server: +OK
Client: list sss
Server: -ERR invalid command
Client: top 1 zz
Server: -ERR invalid command
Client: dele 1
Server: +OK message 1 deleted
Client: quit
Server: +OK POP3 server signing off (2 messages left)

 */
}
