/*     */ package org.jacoco.agent.rt.internal_8ff85ea.asm;
/*     */ 
/*     */ class Handler
/*     */ {
/*     */   Label start;
/*     */   Label end;
/*     */   Label handler;
/*     */   String desc;
/*     */   int type;
/*     */   Handler next;
/*     */ 
/*     */   static Handler remove(Handler h, Label start, Label end)
/*     */   {
/*  84 */     if (h == null) {
/*  85 */       return null;
/*     */     }
/*  87 */     h.next = remove(h.next, start, end);
/*     */ 
/*  89 */     int hstart = h.start.position;
/*  90 */     int hend = h.end.position;
/*  91 */     int s = start.position;
/*  92 */     int e = (end == null) ? 2147483647 : end.position;
/*     */ 
/*  94 */     if ((s < hend) && (e > hstart)) {
/*  95 */       if (s <= hstart) {
/*  96 */         if (e >= hend)
/*     */         {
/*  98 */           h = h.next;
/*     */         }
/*     */         else
/* 101 */           h.start = end;
/*     */       }
/* 103 */       else if (e >= hend)
/*     */       {
/* 105 */         h.end = start;
/*     */       }
/*     */       else {
/* 108 */         Handler g = new Handler();
/* 109 */         g.start = end;
/* 110 */         g.end = h.end;
/* 111 */         g.handler = h.handler;
/* 112 */         g.desc = h.desc;
/* 113 */         g.type = h.type;
/* 114 */         g.next = h.next;
/* 115 */         h.end = start;
/* 116 */         h.next = g;
/*     */       }
/*     */     }
/* 119 */     return h;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.Handler
 * JD-Core Version:    0.5.4
 */